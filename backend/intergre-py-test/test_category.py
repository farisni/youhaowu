"""CategoryController 接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8091/api/product/category"


@pytest.fixture(scope="module")
def base_url():
    try:
        r = requests.get(f"{BASE}/list/tree", timeout=3)
    except (requests.ConnectionError, OSError):
        pytest.skip("后端未启动 (localhost:8091)")
    return BASE


class TestListTree:
    def test_returns_ok(self, base_url):
        r = requests.get(f"{base_url}/list/tree", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert isinstance(body["data"], list)

    def test_data_is_tree_structure(self, base_url):
        r = requests.get(f"{base_url}/list/tree", timeout=5)
        body = r.json()
        data = body["data"]
        if len(data) > 0:
            first = data[0]
            assert "catId" in first
            assert "name" in first
            assert "children" in first


class TestPage:
    def test_returns_ok(self, base_url):
        payload = {"pageNum": 1, "pageSize": 10}
        r = requests.post(f"{base_url}/list", json=payload, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert "total" in body["data"]
        assert "list" in body["data"]

    def test_page_structure(self, base_url):
        payload = {"pageNum": 1, "pageSize": 2}
        r = requests.post(f"{base_url}/list", json=payload, timeout=5)
        body = r.json()
        assert body["code"] == 0
        data = body["data"]
        assert "total" in data
        assert "pageNum" in data
        assert "pageSize" in data
        assert "list" in data
        assert len(data["list"]) <= data["pageSize"]

    def test_name_filter(self, base_url):
        payload = {"pageNum": 1, "pageSize": 10, "name": "手机"}
        r = requests.post(f"{base_url}/list", json=payload, timeout=5)
        body = r.json()
        assert body["code"] == 0

    def test_keyword_search(self, base_url):
        payload = {"pageNum": 1, "pageSize": 10, "keyword": "电子"}
        r = requests.post(f"{base_url}/list", json=payload, timeout=5)
        body = r.json()
        assert body["code"] == 0

    def test_empty_payload_uses_defaults(self, base_url):
        r = requests.post(f"{base_url}/list", json={}, timeout=5)
        body = r.json()
        assert body["code"] == 0
        assert body["data"]["pageSize"] == 10


class TestChildren:
    @pytest.fixture(scope="class")
    def existing_parent_id(self, base_url):
        r = requests.get(f"{base_url}/list/tree", timeout=5)
        data = r.json()["data"]
        if data:
            return data[0]["catId"]
        return None

    def test_returns_ok(self, base_url, existing_parent_id):
        if existing_parent_id is None:
            pytest.skip("无分类数据")
        r = requests.get(f"{base_url}/parent/{existing_parent_id}", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert isinstance(body["data"], list)

    def test_nonexistent_parent_returns_empty(self, base_url):
        r = requests.get(f"{base_url}/parent/999999", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert body["data"] == []

    def test_children_have_correct_fields(self, base_url, existing_parent_id):
        if existing_parent_id is None:
            pytest.skip("无分类数据")
        r = requests.get(f"{base_url}/parent/{existing_parent_id}", timeout=5)
        data = r.json()["data"]
        if len(data) > 0:
            child = data[0]
            assert "catId" in child
            assert "name" in child
            assert "parentCid" in child


class TestDelete:
    def test_returns_ok(self, base_url):
        payload = [999999, 999998]
        r = requests.post(f"{base_url}/delete/1", json=payload, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_empty_list(self, base_url):
        r = requests.post(f"{base_url}/delete/1", json=[], timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0


class TestUpdate:
    @pytest.fixture(scope="class")
    def existing_cat_id(self, base_url):
        r = requests.get(f"{base_url}/list/tree", timeout=5)
        data = r.json()["data"]
        if data:
            return data[0]["catId"]
        return None

    def test_returns_ok(self, base_url, existing_cat_id):
        if existing_cat_id is None:
            pytest.skip("无分类数据")
        payload = {"catId": existing_cat_id, "name": "测试更新名称", "sort": 99}
        r = requests.post(f"{base_url}/update/1", json=payload, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_missing_catid_rejected(self, base_url):
        r = requests.post(f"{base_url}/update/1", json={"name": "无ID"}, timeout=5)
        assert r.status_code == 400


class TestEdgeCases:
    def test_malformed_json(self, base_url):
        r = requests.post(f"{base_url}/list",
                          data="{invalid",
                          headers={"Content-Type": "application/json"},
                          timeout=5)
        # 非法 JSON 体——后端容错，返回正常
        assert r.status_code == 200
        assert r.json()["code"] == 0

    def test_wrong_method_rejected(self, base_url):
        r = requests.delete(f"{base_url}/list/tree", timeout=5)
        assert r.status_code == 405
