"""OrderController 接口验证"""
import pytest
import requests

BASE = "http://localhost:8091/api/order/order"


@pytest.fixture(scope="module")
def base_url():
    try:
        requests.get(f"{BASE}/list", timeout=3)
    except (requests.ConnectionError, OSError):
        pytest.skip("后端未启动")


class TestOrder:
    def test_page_returns_ok(self, base_url):
        r = requests.get(f"{base_url}/list", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0
        assert "list" in body["data"]
