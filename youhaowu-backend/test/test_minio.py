"""MinIO OSS 接口测试（pytest + requests）

测试范围：
1. OSSController /policy 接口 — MinIO presigned PUT URL 生成
2. MinIO 直连 — bucket 可达性验证
"""
import pytest
import requests
import urllib.parse
from datetime import datetime

THIRDPARTY_BASE = "http://192.168.8.112:8096/thirdparty/oss"
MINIO_ENDPOINT = "http://192.168.8.112:9002"


@pytest.fixture(scope="module")
def thirdparty_url():
    """检查 thirdparty 服务是否可访问"""
    try:
        r = requests.get(f"{THIRDPARTY_BASE}/policy", timeout=3)
    except (requests.ConnectionError, requests.Timeout):
        pytest.skip("thirdparty 服务未启动 (192.168.8.112:8096)")
    return THIRDPARTY_BASE


class TestPolicyEndpoint:
    """测试 /thirdparty/oss/policy 接口"""

    def test_returns_ok(self, thirdparty_url):
        """正常返回 code=0"""
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        assert r.status_code == 200
        body = r.json()
        assert body["code"] == 0

    def test_contains_required_fields(self, thirdparty_url):
        """返回字段包含 url, dir, host, expire"""
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        body = r.json()
        data = body["data"]
        for field in ["url", "dir", "host", "expire"]:
            assert field in data, f"缺少字段: {field}"

    def test_url_is_presigned_put(self, thirdparty_url):
        """验证 presigned URL 特征"""
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        body = r.json()
        data = body["data"]
        url = data["url"]
        parsed = urllib.parse.urlparse(url)
        # MinIO presigned URL 使用 http/https
        assert parsed.scheme in ("http", "https")
        assert parsed.hostname is not None
        # MinIO presigned URL 通常包含 X-Amz- 查询参数
        qs = urllib.parse.parse_qs(parsed.query)
        assert any(k.startswith("X-Amz-") for k in qs.keys()), "缺少 X-Amz-* 签名参数"

    def test_dir_is_date_format(self, thirdparty_url):
        """dir 为 yyyy-MM-dd/ 格式"""
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        body = r.json()
        data = body["data"]
        today = datetime.now().strftime("%Y-%m-%d")
        assert data["dir"] == f"{today}/"

    def test_expire_is_future(self, thirdparty_url):
        """expire 为未来的时间戳"""
        import time
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        body = r.json()
        data = body["data"]
        expire_ts = int(data["expire"])
        now_ts = int(time.time())
        assert expire_ts > now_ts, f"expire {expire_ts} 不大于当前时间 {now_ts}"

    def test_presigned_url_allows_upload(self, thirdparty_url):
        """presigned URL 可成功 PUT 上传文件"""
        r = requests.get(f"{thirdparty_url}/policy", timeout=5)
        body = r.json()
        data = body["data"]
        url = data["url"]

        # PUT 上传测试文件
        test_content = f"Hello MinIO test {datetime.now().isoformat()}"
        r2 = requests.put(url, data=test_content, timeout=5)
        assert r2.status_code == 200, f"PUT 上传失败, status={r2.status_code}, body={r2.text[:200]}"


class TestMinIOConnectivity:
    """测试 MinIO 服务连通性"""

    def test_minio_bucket_accessible(self):
        """验证 youhaowu bucket 存在并可访问"""
        try:
            r = requests.get(f"{MINIO_ENDPOINT}/youhaowu", timeout=3)
        except (requests.ConnectionError, requests.Timeout):
            pytest.skip("MinIO 服务不可达 (192.168.8.112:9002)")
        # MinIO 返回 ListBucketResult XML，需要 bucket 存在
        assert r.status_code in (200, 403), f"MinIO 状态异常: {r.status_code}"
