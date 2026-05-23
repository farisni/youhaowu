"""CategoryController 接口测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8091/api/product/category"


#  ═══════════════════════════════════════════════════
#  Fixtures
#  ═══════════════════════════════════════════════════

@pytest.fixture(scope="module")
def base_url():
    """确保后端已启动，否则跳过全部测试"""
    try:
        r = requests.get(f"{BASE}/list/tree", timeout=3)
    except requests.ConnectionError:
        pytest.skip("后端未启动 (localhost:8091)")
    return BASE


#  ═══════════════════════════════════════════════════
#  1. GET /list/tree — 获取分类树
#  ═══════════════════════════════════════════════════

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


#  ═══════════════════════════════════════════════════
#  2. POST /list — 分页查询分类
#  ═══════════════════════════════════════════════════

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
        # 返回条数应不超过声明的 pageSize
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


#  ═══════════════════════════════════════════════════
#  3. GET /parent/{parentId} — 根据父ID查子分类
#  ═══════════════════════════════════════════════════

class TestChildren:
    @pytest.fixture(scope="class")
    def existing_parent_id(self, base_url):
        """从分类树中取一个一级分类ID"""
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


#  ═══════════════════════════════════════════════════
#  4. POST /delete/{id} — 批量删除分类
#  ═══════════════════════════════════════════════════

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


#  ═══════════════════════════════════════════════════
#  5. POST /update/{id} — 修改分类
#  ═══════════════════════════════════════════════════

class TestUpdate:
    @pytest.fixture(scope="class")
    def existing_cat_id(self, base_url):
        """从分类树中取一个分类ID"""
        r = requests.get(f"{base_url}/list/tree", timeout=5)
        data = r.json()["data"]
        if data:
            return data[0]["catId"]
        return None

    def test_returns_ok(self, base_url, existing_cat_id):
        if existing_cat_id is None:
            pytest.skip("无分类数据")
        payload = {
            "catId": existing_cat_id,
            "name": "测试更新名称",
            "sort": 99,
        }
        r = requests.post(f"{base_url}/update/1", json=payload, timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_missing_catid_rejected(self, base_url):
        r = requests.post(f"{base_url}/update/1", json={"name": "无ID"}, timeout=5)
        assert r.status_code == 400


#  ═══════════════════════════════════════════════════
#  6. 边界 & 容错
#  ═══════════════════════════════════════════════════

class TestEdgeCases:
    def test_malformed_json(self, base_url):
        r = requests.post(f"{base_url}/list",
                          data="{invalid",
                          headers={"Content-Type": "application/json"},
                          timeout=5)
        # 非法 JSON 应被拒绝
        assert r.status_code == 400

    def test_wrong_method_rejected(self, base_url):
        r = requests.delete(f"{base_url}/list/tree", timeout=5)
        assert r.status_code == 405
