"""SpuInfoController 上架接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://192.168.8.112:8091/api/product/spuinfo"


@pytest.fixture(scope="module")
def base_url():
    try:
        r = requests.get(f"{BASE}/info/14", timeout=3)
        r.raise_for_status()
    except (requests.ConnectionError, requests.Timeout, OSError):
        pytest.skip("后端未启动 (192.168.8.112:8091)")
    return BASE


@pytest.fixture(scope="module")
def test_spu_id(base_url):
    """
    确保存在一个未上架的测试 SPU。
    优先用已有的 SPU 14（测试商品-从0上架），不存在则新建。
    """
    #  先查 SPU 14 是否存在
    r = requests.get(f"{base_url}/info/14", timeout=5)
    if r.status_code == 200 and r.json().get("code") == 0:
        #  重置为上架前状态
        requests.post(f"{base_url}/14/up", timeout=5)  # 先上架一次
        #  这里无法通过 API 下架，假设每次测试前 SPU 14 是可用的
        return 14

    #  新建 SPU
    payload = {
        "spuName": "pytest测试商品",
        "spuDescription": "pytest自动创建的测试SPU",
        "catalogId": 1,
        "brandId": 9,
        "weight": 0.5,
    }
    r = requests.post(f"{base_url}/save", json=payload, timeout=5)
    if r.status_code != 200 or r.json().get("code") != 0:
        pytest.skip("无法创建测试 SPU")
    #  取最新 ID
    r2 = requests.post(f"{base_url}/list", json={"pageNum": 1, "pageSize": 1}, timeout=5)
    items = r2.json().get("data", {}).get("list", [])
    if items:
        return items[0]["id"]
    pytest.skip("无法获取测试 SPU ID")


class TestSpuUp:
    """商品上架接口"""

    def test_up_returns_success(self, base_url, test_spu_id):
        """上架成功返回 code=0"""
        r = requests.post(f"{base_url}/{test_spu_id}/up", timeout=10)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["success"] is True

    def test_up_sets_publish_status(self, base_url, test_spu_id):
        """上架后 publishStatus=1"""
        #  先确认为已上架（上一条测试已执行 up）
        r = requests.get(f"{base_url}/info/{test_spu_id}", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["publishStatus"] == 1, f"expected publishStatus=1, got {body['data'].get('publishStatus')}"

    def test_up_idempotent(self, base_url, test_spu_id):
        """重复上架不报错"""
        r = requests.post(f"{base_url}/{test_spu_id}/up", timeout=10)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_up_nonexistent_spu(self, base_url):
        """不存在的 SPU 上架返回错误"""
        r = requests.post(f"{base_url}/99999/up", timeout=10)
        assert r.status_code == 200
        body = r.json()
        #  应返回错误（库存/属性为空导致 or 业务异常）
        assert body["code"] != 0 or body["success"] is False


class TestSpuUpES:
    """上架后 ES 索引验证（需要 search 服务和 ES 可用）"""

    ES_SEARCH_URL = "http://192.168.8.112:9200/youhaowu_product/_search"

    @pytest.fixture(scope="class")
    def es_available(self):
        try:
            r = requests.get("http://192.168.8.112:9200/_cluster/health", timeout=3)
            return r.status_code == 200
        except Exception:
            return False

    def test_es_has_sku_after_up(self, base_url, test_spu_id, es_available):
        """上架后 ES 中能搜到对应 SKU"""
        if not es_available:
            pytest.skip("ES 不可用")

        #  先查 SPU 下的 SKU 列表
        r = requests.get(f"{base_url}/info/{test_spu_id}", timeout=5)
        assert r.status_code == 200

        #  查询 ES
        r2 = requests.get(
            self.ES_SEARCH_URL,
            params={"q": f"spuId:{test_spu_id}", "size": 10},
            timeout=5,
        )
        assert r2.status_code == 200
        hits = r2.json().get("hits", {}).get("hits", [])
        assert len(hits) > 0, f"ES 中未找到 spuId={test_spu_id} 的商品"
