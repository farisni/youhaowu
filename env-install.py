#!/usr/bin/env python3
"""服务器环境检查 + 初始化脚本 — 验证 Java/Docker 并自动创建网络、PostgreSQL 和 Nacos。

要求: 脚本必须放在 ~/Code/ 下执行。
用法:
    python3 env-install.py          # 检查 + 自动初始化 + 启动
    python3 env-install.py -q       # 静默模式
"""

import platform
import subprocess
import sys
import re
import os
import textwrap
import urllib.request
import shutil


# ═══════════════════════════════════════════════════════════
#  配置（修改这里统一生效）
# ═══════════════════════════════════════════════════════════

def get_config() -> dict:
    """返回所有可配置项。"""
    return {
        # Docker 网络
        "net_name": "youhaowu_net",
        "net_subnet": "172.20.0.0/16",
        "net_gateway": "172.20.0.1",

        # PostgreSQL
        "pg_image": "postgres:17-alpine",
        "pg_container": "postgres-17",
        "pg_ip": "172.20.0.10",
        "pg_port": "5432",
        "pg_user": "faris",
        "pg_password": "123456",
        "pg_data_dir": "data/postgres/data",
        "pg_init_dir": "data/postgres/init",

        # Nacos 镜像
        "nacos_image": "nacos/nacos-server:v3.1.1-pg",
        "nacos_container": "nacos",
        "nacos_ip": "172.20.0.20",
        "nacos_auth_token": "VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=",
        "nacos_auth_identity_key": "nacos",
        "nacos_auth_identity_value": "nacos",

        # Nacos SQL
        "nacos_sql_url": "https://ghproxy.net/https://raw.githubusercontent.com/lilinhai/nacos-datasource-plugin-ext/master/nacos-postgresql-datasource-plugin-ext/src/main/resources/schema/nacos-pg.sql",
        "nacos_sql_file": "nacos-pg.sql",
    }


# ═══════════════════════════════════════════════════════════
#  工具
# ═══════════════════════════════════════════════════════════

def get_script_dir() -> str:
    """返回脚本所在目录，强制要求 ~/Code，否则退出。"""
    d = os.path.dirname(os.path.abspath(__file__))
    expected = os.path.join(os.path.expanduser("~"), "Code")
    if d != expected:
        print(f"错误: 脚本必须放在 ~/Code 下运行")
        print(f"  当前位置: {d}")
        print(f"  正确位置: {expected}")
        sys.exit(1)
    return d


def shell(cmd: str, timeout: int | None = 15) -> tuple[int, str, str]:
    """执行 shell 命令，返回 (exit_code, stdout, stderr)"""
    result = subprocess.run(
        cmd, shell=True,
        capture_output=True, text=True,
        timeout=timeout
    )
    return result.returncode, result.stdout.strip(), result.stderr.strip()


# ═══════════════════════════════════════════════════════════
#  环境检查
# ═══════════════════════════════════════════════════════════

def check_java() -> tuple[bool, str]:
    """检查 Java 版本 ≥ 17。"""
    code, stdout, stderr = shell("java -version 2>&1")
    if code != 0:
        return False, "Java 未安装或不在 PATH 中"

    combined = stdout + stderr
    m = re.search(r'(\d+)\.(\d+)', combined.replace('"', ''))
    if not m:
        return False, f"无法解析版本号: {combined[:60]}"

    major = int(m.group(1))
    version_str = combined.split('\n')[0].strip() if combined.strip() else f"版本 {m.group(0)}"

    if major >= 17:
        return True, version_str
    return False, f"{version_str} (需要 ≥ 17)"


def check_docker_installed() -> tuple[bool, str]:
    """检查 Docker 是否已安装。"""
    code, stdout, stderr = shell("docker --version 2>&1")
    if code == 0:
        return True, stdout.strip()
    return False, "Docker 未安装"


def check_docker_compose() -> tuple[bool, str]:
    """检查 Docker Compose 是否可用。"""
    code, stdout, stderr = shell("docker compose version 2>&1")
    if code == 0:
        return True, stdout.strip()
    code2, stdout2, _ = shell("docker-compose --version 2>&1")
    if code2 == 0:
        return True, stdout2.strip()
    return False, "Docker Compose 不可用"


def check_docker_running() -> tuple[bool, str]:
    """检查 Docker 守护进程是否运行中。"""
    code, stdout, _ = shell("docker info 2>&1")
    if code == 0:
        return True, "Docker 守护进程运行中"
    return False, f"Docker 未运行: {stdout.split(chr(10))[0] if stdout else '无法连接'}"


# ═══════════════════════════════════════════════════════════
#  初始化
# ═══════════════════════════════════════════════════════════

def create_network(cfg: dict) -> tuple[bool, str]:
    """创建 Docker 网络（已存在则跳过）。"""
    name = cfg["net_name"]
    code, stdout, _ = shell(f"docker network ls -q --filter name=^{name}$")
    if code == 0 and stdout.strip():
        return True, f"网络 {name} 已存在，跳过"

    code, stdout, stderr = shell(
        f"docker network create "
        f"--driver bridge --subnet={cfg['net_subnet']} --gateway={cfg['net_gateway']} "
        f"{name}"
    )
    if code == 0:
        return True, f"网络 {name} 创建成功"
    return False, f"网络创建失败: {stderr[:80]}"


def generate_docker_compose(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """生成 docker-compose.yml（不覆盖已有文件）。"""
    compose_path = os.path.join(script_dir, "docker-compose.yml")
    if os.path.exists(compose_path):
        return True, "docker-compose.yml 已存在，跳过"

    content = textwrap.dedent("""\
        networks:
          {net_name}:
            external: true

        services:
          postgres:
            image: {pg_image}
            container_name: {pg_container}
            environment:
              - POSTGRES_DB=nacos
              - POSTGRES_USER={pg_user}
              - POSTGRES_PASSWORD={pg_password}
            volumes:
              - ./{pg_data_dir}:/var/lib/postgresql/data
              - ./{pg_init_dir}:/docker-entrypoint-initdb.d
            ports:
              - "{pg_port}:5432"
            networks:
              {net_name}:
                ipv4_address: {pg_ip}
            healthcheck:
              test: ["CMD-SHELL", "pg_isready -U {pg_user}"]
              interval: 5s
              timeout: 5s
              retries: 5

          nacos:
            image: {nacos_image}
            container_name: {nacos_container}
            depends_on:
              postgres:
                condition: service_healthy
            environment:
              - MODE=standalone
              - NACOS_AUTH_ENABLE=true
              - NACOS_AUTH_TOKEN={nacos_auth_token}
              - NACOS_AUTH_IDENTITY_KEY={nacos_auth_identity_key}
              - NACOS_AUTH_IDENTITY_VALUE={nacos_auth_identity_value}
              - DB_URL=jdbc:postgresql://{pg_ip}:{pg_port}/{nacos_db}
              - DB_USER={pg_user}
              - DB_PASSWORD={pg_password}
            ports:
              - "8080:8080"
              - "8848:8848"
              - "9848:9848"
            networks:
              {net_name}:
                ipv4_address: {nacos_ip}
    """).format(**cfg)

    try:
        with open(compose_path, 'w') as f:
            f.write(content)
        return True, f"docker-compose.yml 已生成 ({compose_path})"
    except OSError as e:
        return False, f"写入失败: {e}"


def download_nacos_sql(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """下载 Nacos PG 初始化 SQL 并放入 postgres-init/。"""
    dest = os.path.join(script_dir, cfg["nacos_sql_file"])
    if os.path.exists(dest):
        return True, f"{cfg['nacos_sql_file']} 已存在，跳过"
    try:
        urllib.request.urlretrieve(cfg["nacos_sql_url"], dest)
        # 复制到 postgres-init，PG 首次启动自动执行
        init_dir = os.path.join(script_dir, cfg["pg_init_dir"])
        os.makedirs(init_dir, exist_ok=True)
        shutil.copy2(dest, os.path.join(init_dir, cfg["nacos_sql_file"]))
        return True, f"{cfg['nacos_sql_file']} 下载完成，已放入 postgres-init/"
    except Exception as e:
        return False, f"下载失败: {e}"



def start_compose(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """启动 docker compose。"""
    compose_path = os.path.join(script_dir, "docker-compose.yml")
    rc = os.system(f"docker compose -f {compose_path} up -d")
    if rc == 0:
        return True, "容器启动成功"
    return False, f"启动失败 (exit={rc})"


def run_setup(quiet: bool) -> tuple[int, int]:
    """Docker 就绪后自动执行初始化：网络 → compose → SQL → 数据库 → 启动。"""
    cfg = get_config()
    script_dir = os.path.dirname(os.path.abspath(__file__))
    passed, failed = 0, 0

    if not quiet:
        print()
        print("-" * 40)
        print("  初始化基础设施")
        print("-" * 40)

    # 创建 Docker 网络
    ok, msg = create_network(cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 生成 docker-compose.yml
    ok, msg = generate_docker_compose(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 创建数据目录
    for d in [cfg["pg_data_dir"], cfg["pg_init_dir"]]:
        dir_path = os.path.join(script_dir, d)
        try:
            os.makedirs(dir_path, exist_ok=True)
            _print_result(quiet, True, f"目录 {d}/ 已就绪")
            passed += 1
        except OSError as e:
            _print_result(quiet, False, f"目录 {d}/ 创建失败: {e}")
            failed += 1

    # 下载 Nacos SQL
    ok, msg = download_nacos_sql(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 启动容器
    ok, msg = start_compose(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    return passed, failed


def _print_result(quiet: bool, ok: bool, msg: str):
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")


def _count(passed: int, failed: int, ok: bool) -> tuple[int, int]:
    if ok:
        return passed + 1, failed
    return passed, failed + 1


def main():
    get_script_dir()
    quiet = "-q" in sys.argv or "--quiet" in sys.argv

    checks = [
        ("Java", check_java),
        ("Docker", check_docker_installed),
        ("Docker Compose", check_docker_compose),
        ("Docker 运行中", check_docker_running),
    ]

    passed = 0
    failed = 0

    if not quiet:
        print("=" * 40)
        print("  服务器环境检查")
        print("=" * 40)
        print(f"主机名: {platform.node()}")
        print(f"内核: {platform.system()} {platform.release()}")
        print()

    for name, fn in checks:
        ok, msg = fn()
        status = "✓" if ok else "✗"
        if ok:
            passed += 1
        else:
            failed += 1
        if not quiet:
            print(f"[{status}] {name}: {msg}")

    if failed == 0:
        sp, sf = run_setup(quiet)
        passed += sp
        failed += sf

    if not quiet:
        print()
        print("=" * 40)
        print(f"  结果: {passed}/{passed + failed} 通过")
        print("=" * 40)

    sys.exit(0 if failed == 0 else 1)


if __name__ == "__main__":
    main()
