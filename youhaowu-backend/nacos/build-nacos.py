#!/usr/bin/env python3
"""构建带 PostgreSQL 插件的 Nacos 镜像 + 初始化数据库。

用法:
    python3 build-nacos.py           # 增量构建
    python3 build-nacos.py --force   # 强制全部重跑
"""

import os
import sys
import shutil
import subprocess
import urllib.request
import zipfile


# ═══════════════════════════════════════════════════════════
#  配置
# ═══════════════════════════════════════════════════════════

NACOS_VERSION = "3.1.1"
NACOS_SQL_URL = "https://ghproxy.net/https://raw.githubusercontent.com/lilinhai/nacos-datasource-plugin-ext/master/nacos-postgresql-datasource-plugin-ext/src/main/resources/schema/nacos-pg.sql"
NACOS_SQL = "nacos-pg.sql"
PLUGIN_JAR_URL = "https://repo1.maven.org/maven2/com/sinhy/nacos-postgresql-datasource-plugin-ext/3.1.1/nacos-postgresql-datasource-plugin-ext-3.1.1.jar"
PLUGIN_JAR = "nacos-postgresql-datasource-plugin-ext-3.1.1.jar"
PG_DRIVER_URL = "https://maven.aliyun.com/repository/central/org/postgresql/postgresql/42.7.4/postgresql-42.7.4.jar"
PG_DRIVER_JAR = "postgresql-42.7.4.jar"
PG_CONTAINER = "postgres-17"
PG_USER = "faris"
NACOS_DB = "nacos"
DOCKER_IMAGE = f"nacos/nacos-server:v{NACOS_VERSION}"
CUSTOM_IMAGE = f"nacos/nacos-server:v{NACOS_VERSION}-pg"

WORK_DIR = os.path.dirname(os.path.abspath(__file__))


# ═══════════════════════════════════════════════════════════
#  工具
# ═══════════════════════════════════════════════════════════

def run(cmd: str, cwd: str | None = None) -> tuple[int, str]:
    print(f"  -> {cmd}")
    try:
        result = subprocess.run(
            cmd, shell=True, cwd=cwd,
            capture_output=True, text=True
        )
    except Exception as e:
        print(f"    异常: {e}")
        return 1, str(e)
    out = (result.stdout + result.stderr).strip()
    if out:
        for line in out.split('\n')[-8:]:
            print(f"    {line}")
    lines = out.split('\n') if out else []
    return result.returncode, lines[-1] if lines else ""


def step(num: int, title: str):
    print(f"\n{'=' * 50}")
    print(f"  步骤 {num}: {title}")
    print(f"{'=' * 50}")


def done(msg: str):
    print(f"  [OK] {msg}")


def fail(msg: str):
    print(f"  [FAIL] {msg}")
    sys.exit(1)


def download(url: str, dest: str, label: str, force: bool):
    if os.path.exists(dest) and not force:
        done(f"{label} 已存在，跳过")
        return
    print(f"  -> 下载 {label} ...")
    urllib.request.urlretrieve(url, dest)
    done(f"{label} 下载完成")


# ═══════════════════════════════════════════════════════════
#  步骤 1-2: 下载 JAR
# ═══════════════════════════════════════════════════════════

def download_plugin(force: bool):
    step(1, "下载 PostgreSQL 插件")
    download(PLUGIN_JAR_URL, os.path.join(WORK_DIR, PLUGIN_JAR),
             PLUGIN_JAR, force)


def download_pg(force: bool):
    step(2, "下载 PostgreSQL JDBC 驱动")
    download(PG_DRIVER_URL, os.path.join(WORK_DIR, PG_DRIVER_JAR),
             PG_DRIVER_JAR, force)


# ═══════════════════════════════════════════════════════════
#  步骤 3: 提取官方 nacos-server.jar
# ═══════════════════════════════════════════════════════════

def extract_jar(force: bool):
    step(3, "提取官方 nacos-server.jar")
    jar_path = os.path.join(WORK_DIR, "nacos-server.jar")

    if os.path.exists(jar_path) and not force:
        done("nacos-server.jar 已存在，跳过")
        return

    rc, _ = run(f"docker pull {DOCKER_IMAGE}")
    if rc != 0:
        fail("拉取 Docker 镜像失败")

    run("docker rm -f nacos-tmp 2>/dev/null || true")
    rc, _ = run(f"docker create --name nacos-tmp {DOCKER_IMAGE}")
    if rc != 0:
        fail("创建临时容器失败")

    rc, _ = run(f"docker cp nacos-tmp:/home/nacos/target/nacos-server.jar {jar_path}")
    if rc != 0:
        fail("复制 nacos-server.jar 失败")

    run("docker rm -f nacos-tmp 2>/dev/null || true")
    done("nacos-server.jar 提取完成")


# ═══════════════════════════════════════════════════════════
#  步骤 4: 注入 JAR
# ═══════════════════════════════════════════════════════════

def inject_jars(force: bool):
    step(4, "注入插件 + PG 驱动")
    jar_path = os.path.join(WORK_DIR, "nacos-server.jar")

    if not force:
        try:
            with zipfile.ZipFile(jar_path, 'r') as zf:
                names = zf.namelist()
            if any(PG_DRIVER_JAR in n for n in names):
                done("JAR 已注入过，跳过")
                return
        except Exception:
            pass

    jars = [
        (os.path.join(WORK_DIR, PLUGIN_JAR), f"BOOT-INF/lib/{PLUGIN_JAR}"),
        (os.path.join(WORK_DIR, PG_DRIVER_JAR), f"BOOT-INF/lib/{PG_DRIVER_JAR}"),
    ]

    for src, arcname in jars:
        print(f"  -> 注入 {arcname} ...")
        try:
            with zipfile.ZipFile(jar_path, 'a', zipfile.ZIP_DEFLATED) as zf:
                zf.write(src, arcname)
        except Exception as e:
            fail(f"注入失败: {e}")

    done("注入完成")


# ═══════════════════════════════════════════════════════════
#  步骤 5: 构建 Docker 镜像
# ═══════════════════════════════════════════════════════════

def build_image(force: bool):
    step(5, "构建 Docker 镜像")

    if not force:
        result = subprocess.run(
            f"docker images -q {CUSTOM_IMAGE}", shell=True,
            capture_output=True, text=True
        )
        if result.stdout.strip():
            done(f"镜像 {CUSTOM_IMAGE} 已存在，跳过")
            return

    dockerfile = os.path.join(WORK_DIR, "Dockerfile")
    with open(dockerfile, 'w') as f:
        f.write(f"FROM {DOCKER_IMAGE}\n")
        f.write("COPY nacos-server.jar /home/nacos/target/nacos-server.jar\n")

    rc, _ = run(f"docker build -t {CUSTOM_IMAGE} .", cwd=WORK_DIR)
    if rc != 0:
        fail("构建镜像失败")

    os.remove(dockerfile)
    done(f"镜像 {CUSTOM_IMAGE} 构建完成")


# ═══════════════════════════════════════════════════════════
#  步骤 6: 下载 Nacos 初始化 SQL
# ═══════════════════════════════════════════════════════════

def download_sql(force: bool):
    step(6, "下载 Nacos 初始化 SQL")
    download(NACOS_SQL_URL, os.path.join(WORK_DIR, NACOS_SQL),
             NACOS_SQL, force)


# ═══════════════════════════════════════════════════════════
#  步骤 7: 初始化 Nacos 数据库
# ═══════════════════════════════════════════════════════════

def init_db(force: bool):
    step(7, "初始化 Nacos 数据库")

    # 检查容器是否在运行
    rc, _ = run(f"docker ps -q --filter name=^{PG_CONTAINER}$")
    if rc != 0:
        fail(f"容器 {PG_CONTAINER} 未运行，请先启动 PostgreSQL")

    # 检查 nacos 数据库是否已存在
    check = subprocess.run(
        f"docker exec {PG_CONTAINER} psql -U {PG_USER} -d postgres -lqt | cut -d '|' -f1 | grep -qw {NACOS_DB}",
        shell=True, capture_output=True, text=True
    )
    if check.returncode == 0 and not force:
        done(f"数据库 {NACOS_DB} 已存在，跳过")
        return

    # 创建 nacos 数据库（连默认 postgres 库）
    print(f"  -> 创建 {NACOS_DB} 数据库 ...")
    run(f"docker exec {PG_CONTAINER} psql -U {PG_USER} -d postgres "
        f"-c 'CREATE DATABASE {NACOS_DB};'")

    # 执行初始化 SQL
    sql_path = os.path.join(WORK_DIR, NACOS_SQL)
    print("  -> 执行 nacos-pg.sql ...")
    rc, _ = run(
        f"docker exec -i {PG_CONTAINER} psql -U {PG_USER} -d {NACOS_DB} < {sql_path}"
    )
    if rc != 0:
        fail("初始化 SQL 执行失败")

    done("Nacos 数据库初始化完成")


# ═══════════════════════════════════════════════════════════
#  主流程
# ═══════════════════════════════════════════════════════════

def main():
    force = "--force" in sys.argv or "-f" in sys.argv
    os.chdir(WORK_DIR)

    print("=" * 50)
    print(f"  Nacos {NACOS_VERSION} + PostgreSQL 插件构建")
    print(f"  工作目录: {WORK_DIR}")
    if force:
        print("  模式: 强制重跑")
    print("=" * 50)

    download_plugin(force)
    download_pg(force)
    extract_jar(force)
    inject_jars(force)
    build_image(force)
    download_sql(force)
    init_db(force)

    print()
    print("=" * 50)
    print("  OK 全部完成!")
    print()
    print("  产物:")
    print(f"    JAR:    {WORK_DIR}/nacos-server.jar")
    print(f"    镜像:   {CUSTOM_IMAGE}")
    print(f"    SQL:    {WORK_DIR}/{NACOS_SQL}")
    print(f"    数据库: {PG_CONTAINER} / {NACOS_DB} (已初始化)")
    print()
    print("  启动 Nacos:")
    print(f"    docker run -d --name nacos \\")
    print(f"      --network youhaowu_net --ip 172.20.0.20 \\")
    print(f"      -p 8848:8848 -p 9848:9848 \\")
    print(f"      -e MODE=standalone \\")
    print(f"      -e SPRING_DATASOURCE_PLATFORM=postgresql \\")
    print(f"      -e DB_URL=jdbc:postgresql://172.20.0.10:5432/{NACOS_DB} \\")
    print(f"      -e DB_USER={PG_USER} \\")
    print(f"      -e DB_PASSWORD=123456 \\")
    print(f"      {CUSTOM_IMAGE}")
    print()
    print("  登录: http://<IP>:8848/nacos  (nacos/nacos)")
    print("=" * 50)


if __name__ == "__main__":
    main()
