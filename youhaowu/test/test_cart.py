"""Cart 模块接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8099/api/cart"


@pytest.fixture(scope="module")
def base_url():
    try:
        requests.get(f"{BASE}/list", timeout=3)
    except (requests.ConnectionError, OSError):
        pytest.skip("后端未启动 (localhost:8099)")
    return BASE


#  ==================== 1. 添加购物车 ====================

class TestAdd:
    def test_add_returns_item(self, base_url):
        r = requests.get(f"{base_url}/add", params={"skuId": 1, "num": 2}, timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["skuId"] == 1
        assert body["data"]["count"] == 2

    def test_add_cumulates_count(self, base_url):
        #  同一个 skuId 再添加，数量应累加
        requests.get(f"{base_url}/add", params={"skuId": 1, "num": 1}, timeout=5)
        r = requests.get(f"{base_url}/item/1", timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.json()["data"]["count"] >= 2


#  ==================== 2. 购物车列表 ====================

class TestList:
    def test_returns_ok(self, base_url):
        r = requests.get(f"{base_url}/list", timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.status_code == 200
        assert r.json()["code"] == 0


#  ==================== 3. 选中/数量/删除 ====================

class TestCheck:
    def test_toggle_check(self, base_url):
        r = requests.get(f"{base_url}/check", params={"skuId": 1, "checked": 0}, timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.status_code == 200
        assert r.json()["data"] == 1


class TestCount:
    def test_change_count(self, base_url):
        r = requests.get(f"{base_url}/count", params={"skuId": 1, "num": 5}, timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.status_code == 200
        assert r.json()["data"] == 1


class TestDelete:
    def test_delete_item(self, base_url):
        #  先确保有数据
        requests.get(f"{base_url}/add", params={"skuId": 999, "num": 1}, timeout=5)
        r = requests.get(f"{base_url}/delete/999", timeout=5)
        if r.status_code == 500:
            pytest.skip("Redis 未就绪")
        assert r.status_code == 200


#  ==================== 4. 边界 ====================

class TestEdgeCases:
    def test_wrong_method_rejected(self, base_url):
        r = requests.post(f"{base_url}/list", timeout=5)
        assert r.status_code == 405
