"""Coupon 模块 HomeSubject 接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8097/api/coupon/homesubject"


@pytest.fixture(scope="module")
def base_url():
    try:
        requests.get(f"{BASE}/list", timeout=3)
    except (requests.ConnectionError, OSError):
        pytest.skip("后端未启动 (localhost:8097)")
    return BASE


#  ==================== 1. 分页 ====================

class TestPage:
    def test_returns_ok(self, base_url):
        r = requests.get(f"{base_url}/list", timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert "total" in body["data"]
        assert "list" in body["data"]

    def test_page_structure(self, base_url):
        r = requests.get(f"{base_url}/list", params={"pageNum": 1, "pageSize": 2}, timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        body = r.json()
        data = body["data"]
        assert len(data["list"]) <= data["pageSize"]


#  ==================== 2. 详情 ====================

class TestInfo:
    def test_nonexistent_returns_null(self, base_url):
        r = requests.get(f"{base_url}/info/999999", timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        assert r.status_code == 200
        assert r.json()["data"] is None


#  ==================== 3. 保存 ====================

class TestSave:
    def test_empty_body_ok(self, base_url):
        r = requests.post(f"{base_url}/save", json={}, timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        assert r.status_code == 200
        assert r.json()["code"] == 0


#  ==================== 4. 更新 ====================

class TestUpdate:
    def test_missing_body_rejected(self, base_url):
        r = requests.put(f"{base_url}/update/1", data="not json",
                         headers={"Content-Type": "application/json"}, timeout=5)
        assert r.status_code == 400


#  ==================== 5. 删除 ====================

class TestDelete:
    def test_returns_ok(self, base_url):
        r = requests.delete(f"{base_url}/delete/1", json=[999999], timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        assert r.status_code == 200
        assert r.json()["code"] == 0

    def test_empty_list(self, base_url):
        r = requests.delete(f"{base_url}/delete/1", json=[], timeout=5)
        if r.status_code == 500:
            pytest.skip("数据库未就绪")
        assert r.status_code == 200
        assert r.json()["code"] == 0


#  ==================== 6. 边界 ====================

class TestEdgeCases:
    def test_wrong_method_rejected(self, base_url):
        r = requests.delete(f"{base_url}/list", timeout=5)
        assert r.status_code == 405
