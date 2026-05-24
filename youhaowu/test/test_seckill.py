"""youhaowu-seckill 集成测试（pytest + requests）"""
import pytest
import requests

BASE = "http://localhost:8100/api/seckill"


class TestCurrent:
    """查询当前秒杀商品列表"""

    def test_returns_ok(self):
        r = requests.get(f"{BASE}/current", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_missing_auth_ok(self):
        """无登录也可查看秒杀商品"""
        r = requests.get(f"{BASE}/current", timeout=5)
        assert r.status_code == 200


class TestSkuInfo:
    """查询单 SKU 秒杀信息"""

    def test_returns_ok_for_any_id(self):
        r = requests.get(f"{BASE}/sku/1", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_missing_auth_ok(self):
        """无登录也可查看商品秒杀信息"""
        r = requests.get(f"{BASE}/sku/999", timeout=5)
        assert r.status_code == 200


class TestKill:
    """秒杀下单"""

    def test_no_login_rejected(self):
        """未登录秒杀返回 401"""
        r = requests.get(f"{BASE}/kill?killId=1_1&key=abc&num=1", timeout=5)
        assert r.status_code == 401


class TestEdgeCases:
    """边界场景"""

    def test_wrong_method_rejected(self):
        r = requests.post(f"{BASE}/current", timeout=5)
        assert r.status_code == 405
