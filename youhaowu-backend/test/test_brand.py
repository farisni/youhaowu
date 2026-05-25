"""BrandController 接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8091/api/product/brand"


@pytest.fixture(scope="module")
def base_url():
    try:
        r = requests.get(f"{BASE}/list", params={"pageNum": 1, "pageSize": 1}, timeout=3)
    except (requests.ConnectionError, OSError):
        pytest.skip("后端未启动 (localhost:8091)")
    return BASE


@pytest.fixture(scope="module")
def created_brand_id(base_url):
    """创建一个测试品牌，返回 brandId，teardown 时删除"""
    r = requests.post(f"{base_url}/save", json={"brandName": "pytest测试品牌"}, timeout=5)
    body = r.json()
    brand_id = body["data"]
    yield brand_id
    requests.post(f"{base_url}/delete/{brand_id}", timeout=5)


class TestPage:
    def test_returns_ok(self, base_url):
        r = requests.get(f"{base_url}/list", params={"pageNum": 1, "pageSize": 10}, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert "total" in body["data"]
        assert "list" in body["data"]

    def test_page_structure(self, base_url):
        r = requests.get(f"{base_url}/list", params={"pageNum": 1, "pageSize": 2}, timeout=5)
        body = r.json()
        assert body["code"] == 0
        data = body["data"]
        assert "total" in data
        assert "pageNum" in data
        assert "pageSize" in data
        assert "list" in data
        assert len(data["list"]) <= data["pageSize"]

    def test_empty_params_uses_defaults(self, base_url):
        r = requests.get(f"{base_url}/list", timeout=5)
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["pageSize"] == 10

    def test_large_page_num_auto_adjusts(self, base_url):
        """MyBatis-Plus 超大页码自动修正为最后一页，不会返回空列表"""
        r = requests.get(f"{base_url}/list", params={"pageNum": 9999, "pageSize": 10}, timeout=5)
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["pageNum"] <= 9999


class TestInfo:
    def test_returns_ok(self, base_url, created_brand_id):
        r = requests.get(f"{base_url}/info/{created_brand_id}", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert "brandId" in body["data"]
        assert "brandName" in body["data"]

    def test_nonexistent_brand(self, base_url):
        """查询不存在的品牌返回空 VO（brandId=null），而非 null"""
        r = requests.get(f"{base_url}/info/999999", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["brandId"] is None


class TestSave:
    def test_returns_id(self, base_url):
        r = requests.post(f"{base_url}/save", json={"brandName": "pytest新建品牌"}, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["data"] is not None
        # 清理
        requests.post(f"{base_url}/delete/{body['data']}", timeout=5)

    def test_empty_body_accepted(self, base_url):
        """save 没有 @Valid 校验，空 body 也会被接受并插入"""
        r = requests.post(f"{base_url}/save", json={}, timeout=5)
        assert r.status_code == 200
        assert r.json()["code"] == 0
        # 清理
        requests.post(f"{base_url}/delete/{r.json()['data']}", timeout=5)


class TestUpdate:
    def test_missing_body_rejected(self, base_url):
        r = requests.post(f"{base_url}/update/1", timeout=5)
        assert r.status_code == 400


class TestDelete:
    def test_returns_ok(self, base_url):
        # 先创建再删除
        r = requests.post(f"{base_url}/save", json={"brandName": "待删除品牌"}, timeout=5)
        new_id = r.json()["data"]
        r2 = requests.post(f"{base_url}/delete/{new_id}", timeout=5)
        assert r2.status_code == 200
        body = r2.json()
        assert body["code"] == 0

    def test_nonexistent_id_ok(self, base_url):
        r = requests.post(f"{base_url}/delete/999999", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0


class TestEdgeCases:
    def test_wrong_method_rejected(self, base_url):
        r = requests.delete(f"{base_url}/list", timeout=5)
        assert r.status_code == 405

    def test_path_traversal_resolves_to_404(self, base_url):
        """../admin 被浏览器/requests 规范化为 /product/admin，该路径不存在"""
        r = requests.get(f"{base_url}/info/../admin", timeout=5)
        assert r.status_code == 404
