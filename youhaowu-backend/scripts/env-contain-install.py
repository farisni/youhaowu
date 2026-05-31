#!/usr/bin/env python3
"""服务器环境检查 + 初始化脚本 — 验证 Java/Docker 并自动创建网络、PostgreSQL 和 Nacos。

要求: 脚本必须放在 ~/Code/ 下执行。
用法:
    python3 env-install.py          # 检查 + 自动初始化 + 启动
    python3 env-install.py -q       # 静默模式，只输出结果
    python3 env-install.py -k       # 跳过已存在的容器，不报错
"""

import platform
import subprocess
import sys
import re
import os
import textwrap
import urllib.request


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
        "host_ip": "192.168.2.129",

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
        "nacos_db": "nacos",

        # Nacos SQL
        "nacos_sql_url": "https://ghproxy.net/https://raw.githubusercontent.com/lilinhai/nacos-datasource-plugin-ext/master/nacos-postgresql-datasource-plugin-ext/src/main/resources/schema/nacos-pg.sql",
        "nacos_sql_file": "nacos-pg.sql",

        # Redis
        "redis_image": "redis:7-alpine",
        "redis_container": "redis-7",
        "redis_ip": "172.20.0.5",
        "redis_port": "6379",
        "redis_data_dir": "data/redis",

        # Kafka
        "kafka_image": "bitnami/kafka:3.9",
        "kafka_container": "youhaowu-kafka",
        "kafka_ip": "172.20.0.3",
        "kafka_port": "9092",
        "kafka_data_dir": "data/kafka",

        # Kafka UI
        "kafka_ui_image": "provectuslabs/kafka-ui:latest",
        "kafka_ui_container": "youhaowu-kafka-ui",
        "kafka_ui_ip": "172.20.0.4",
        "kafka_ui_port": "9090",

        # Elasticsearch
        "es_image": "elasticsearch:7.17.21",
        "es_container": "elasticsearch-7.17",
        "es_ip": "172.20.0.6",
        "es_port": "9200",
        "es_data_dir": "data/elasticsearch/data",
        "es_config_dir": "data/elasticsearch/config",
        "es_plugins_dir": "data/elasticsearch/plugins",

        # Kibana
        "kibana_image": "kibana:7.17.21",
        "kibana_container": "kibana-7.17",
        "kibana_ip": "172.20.0.7",
        "kibana_port": "5601",

        # Nginx（前端静态 + 反代 Gateway）
        "nginx_image": "nginx:1.13.10",
        "nginx_container": "nginx-1.13",
        "nginx_ip": "172.20.0.8",
        "nginx_port": "80",
        "nginx_html_dir": "data/nginx/html",
        "nginx_conf_dir": "data/nginx/conf",
        "nginx_log_dir": "data/nginx/logs",

        # Jenkins
        "jenkins_image": "jenkins/jenkins:lts-jdk17",
        "jenkins_container": "jenkins",
        "jenkins_port": "8070",
        "jenkins_agent_port": "50000",
        "jenkins_data_dir": "data/jenkins",
        "jenkins_ip": "172.20.0.9",

        "es_ik_url": "https://release.infinilabs.com/analysis-ik/stable/elasticsearch-analysis-ik-7.17.21.zip",
        "es_ik_dir": "analysis-ik",
        "es_ik_custom_words": [
            "有好物", "电商",  "购物车", "秒杀",
            "拼团", "满减", "限时购", "优惠券", "砍价",
            "积分商城", "商品详情", "库存", "商品规格",
        ],
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


def check_nacos_image() -> tuple[bool, str]:
    """检查 nacos/nacos-server:v3.1.1-pg 镜像是否存在。"""
    cfg = get_config()
    image = cfg["nacos_image"]
    code, stdout, _ = shell(f"docker image inspect {image} 2>&1")
    if code == 0:
        return True, f"Nacos 镜像 {image} 已就绪"
    return False, f"Nacos 镜像 {image} 不存在，请先运行 build-nacos-pg-image.py 生成"


def check_maven() -> tuple[bool, str]:
    """检查 Maven 是否已安装。"""
    code, stdout, _ = shell("mvn --version 2>&1")
    if code == 0:
        ver = stdout.split(chr(10))[0] if stdout else "unknown"
        return True, f"Maven: {ver}"
    return False, "Maven 未安装，请执行: sudo yum install -y maven"


def check_docker_gid() -> tuple[bool, str]:
    """检查 docker 组是否存在。"""
    code, stdout, _ = shell("getent group docker 2>&1")
    if code == 0 and stdout.strip():
        return True, "docker 组存在"
    return False, "docker 组不存在，请执行: sudo groupadd docker"


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
              - NACOS_AUTH_ENABLE=false
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

          kafka:
            image: {kafka_image}
            container_name: {kafka_container}
            ports:
              - "{kafka_port}:9092"
            environment:
              - KAFKA_CFG_NODE_ID=1
              - KAFKA_CFG_PROCESS_ROLES=broker,controller
              - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@localhost:9093
              - KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
              - KAFKA_CFG_LISTENERS=PLAINTEXT://:{kafka_port},CONTROLLER://:9093
              - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://{kafka_ip}:{kafka_port}
              - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
              - KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=true
            volumes:
              - ./{kafka_data_dir}:/bitnami/kafka
            networks:
              {net_name}:
                ipv4_address: {kafka_ip}
            restart: unless-stopped

          kafka-ui:
            image: {kafka_ui_image}
            container_name: {kafka_ui_container}
            ports:
              - "{kafka_ui_port}:8080"
            environment:
              - KAFKA_CLUSTERS_0_NAME=youhaowu
              - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS={kafka_container}:{kafka_port}
            depends_on:
              - kafka
            networks:
              {net_name}:
                ipv4_address: {kafka_ui_ip}
            restart: unless-stopped

          elasticsearch:
            image: {es_image}
            container_name: {es_container}
            environment:
              - discovery.type=single-node
              - ES_JAVA_OPTS=-Xms64m -Xmx512m
            volumes:
              - ./{es_config_dir}/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
              - ./{es_data_dir}:/usr/share/elasticsearch/data
              - ./{es_plugins_dir}:/usr/share/elasticsearch/plugins
            ports:
              - "{es_port}:9200"
            networks:
              {net_name}:
                ipv4_address: {es_ip}
            restart: unless-stopped
            healthcheck:
              test: ["CMD-SHELL", "curl -s http://localhost:9200"]
              interval: 10s
              timeout: 5s
              retries: 10

          kibana:
            depends_on:
              elasticsearch:
                condition: service_healthy
            image: {kibana_image}
            container_name: {kibana_container}
            environment:
              - ELASTICSEARCH_HOSTS=http://{es_ip}:9200
            ports:
              - "{kibana_port}:5601"
            networks:
              {net_name}:
                ipv4_address: {kibana_ip}
            restart: unless-stopped

          redis:
            image: {redis_image}
            container_name: {redis_container}
            command: redis-server --appendonly yes
            volumes:
              - ./{redis_data_dir}:/data
            ports:
              - "{redis_port}:6379"
            networks:
              {net_name}:
                ipv4_address: {redis_ip}
            healthcheck:
              test: ["CMD", "redis-cli", "ping"]
              interval: 5s
              timeout: 3s
              retries: 5

          nginx:
            image: {nginx_image}
            container_name: {nginx_container}
            ports:
              - "{nginx_port}:80"
            volumes:
              - ./{nginx_html_dir}:/usr/share/nginx/html
              - ./{nginx_conf_dir}:/etc/nginx
              - ./{nginx_log_dir}:/var/log/nginx
            extra_hosts:
              # host-gateway 是 Docker 内置值，自动解析为宿主机 IP，
              # 容器内通过 host.docker.internal 即可访问宿主机上的 gateway 服务
              - "host.docker.internal:host-gateway"
            networks:
              {net_name}:
                ipv4_address: {nginx_ip}
            restart: unless-stopped

          jenkins:
            image: {jenkins_image}
            container_name: {jenkins_container}
            ports:
              - "{jenkins_port}:8080"
              - "{jenkins_agent_port}:50000"
            volumes:
              - ./{jenkins_data_dir}:/var/jenkins_home
              - ./{jenkins_data_dir}/workspace:/var/jenkins_home/workspace
              - /home/faris/.m2/settings.xml:/var/jenkins_home/.m2/settings.xml
              - /home/faris/.m2/repository:/var/jenkins_home/.m2/repository
              - /var/run/docker.sock:/var/run/docker.sock
              - /usr/bin/docker:/usr/bin/docker
            group_add:
              - "961"   # docker 组 GID，允许容器内调用宿主机 Docker
            networks:
              {net_name}:
                ipv4_address: {jenkins_ip}
            restart: unless-stopped
    """).format(**cfg)

    try:
        with open(compose_path, 'w') as f:
            f.write(content)
        return True, f"docker-compose.yml 已生成 ({compose_path})"
    except OSError as e:
        return False, f"写入失败: {e}"


def download_nacos_sql(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """下载 Nacos PG 初始化 SQL 到 postgres-init/。"""
    init_dir = os.path.join(script_dir, cfg["pg_init_dir"])
    os.makedirs(init_dir, exist_ok=True)
    dest = os.path.join(init_dir, cfg["nacos_sql_file"])

    if os.path.exists(dest):
        return True, f"{cfg['nacos_sql_file']} 已存在，跳过"

    try:
        urllib.request.urlretrieve(cfg["nacos_sql_url"], dest)
        return True, f"{cfg['nacos_sql_file']} 下载完成"
    except Exception as e:
        return False, f"下载失败: {e}"




def generate_es_config(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """生成 elasticsearch.yml。"""
    es_conf_dir = os.path.join(script_dir, cfg["es_config_dir"])
    os.makedirs(es_conf_dir, exist_ok=True)
    yml_path = os.path.join(es_conf_dir, "elasticsearch.yml")
    if os.path.exists(yml_path):
        return True, "elasticsearch.yml 已存在，跳过"
    with open(yml_path, 'w') as f:
        f.write("http.host: 0.0.0.0\n")
    return True, "elasticsearch.yml 已生成"



    return True, "elasticsearch.yml 已生成"


def generate_nginx_conf(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """首次生成默认 nginx.conf（反代 /api/ 到宿主机 gateway:8888）。"""
    conf_dir = os.path.join(script_dir, cfg["nginx_conf_dir"])
    os.makedirs(conf_dir, exist_ok=True)
    conf_path = os.path.join(conf_dir, "nginx.conf")
    if os.path.exists(conf_path):
        return True, "nginx.conf 已存在，跳过"

    _nginx_conf = textwrap.dedent("""\
        events {{
            worker_connections 1024;
        }}

        http {{
            # 注：conf 目录挂载会覆盖默认 mime.types，故去掉 include
            default_type application/octet-stream;

            server {{
                listen 80;
                server_name localhost;

                # /api/ 请求反向代理到宿主机上的 gateway 服务
                location /api/ {{
                    proxy_pass http://host.docker.internal:8888/;
                    proxy_set_header Host $host;
                }}

                # 其余请求走前端静态文件
                location / {{
                    root /usr/share/nginx/html;
                    index index.html;
                    try_files $uri $uri/ /index.html;
                }}
            }}
        }}
        """)

    # 去掉双花括号的 escape（dedent 导致的）
    _nginx_conf = _nginx_conf.replace("{{", "{").replace("}}", "}")

    try:
        with open(conf_path, "w", encoding="utf-8") as f:
            f.write(_nginx_conf)
        return True, f"nginx.conf 已生成 ({cfg['nginx_conf_dir']}/)"
    except OSError as e:
        return False, f"nginx.conf 写入失败: {e}"


def install_es_ik(script_dir: str, cfg: dict) -> tuple[bool, str]:
    """下载并安装 IK 分词插件，含自定义词典。"""
    plugins_dir = os.path.join(script_dir, cfg["es_plugins_dir"])
    ik_dir = os.path.join(plugins_dir, cfg["es_ik_dir"])
    if os.path.exists(ik_dir):
        #  已安装也要确保词典存在（升级场景）
        _write_ik_dicts(ik_dir, cfg)
        return True, "IK 插件已安装，跳过"

    zip_path = os.path.join(script_dir, "ik.zip")
    try:
        urllib.request.urlretrieve(cfg["es_ik_url"], zip_path)
    except Exception as e:
        return False, f"IK 下载失败: {e}"

    # 解压
    import zipfile
    os.makedirs(ik_dir, exist_ok=True)
    with zipfile.ZipFile(zip_path, 'r') as zf:
        zf.extractall(ik_dir)
    os.remove(zip_path)

    # 写入自定义词典配置
    _write_ik_dicts(ik_dir, cfg)
    return True, "IK 分词插件安装完成"


def _write_ik_dicts(ik_dir: str, cfg: dict):
    """生成 IKAnalyzer.cfg.xml + custom.dic 到 IK 插件 config 目录。"""
    ik_config_dir = os.path.join(ik_dir, "config")
    os.makedirs(ik_config_dir, exist_ok=True)

    # IKAnalyzer.cfg.xml（强制覆盖，因为原版 ext_dict 为空）
    xml_path = os.path.join(ik_config_dir, "IKAnalyzer.cfg.xml")
    xml_content = textwrap.dedent("""\
        <?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
        <properties>
            <comment>IK Analyzer 扩展配置</comment>
            <entry key="ext_dict">custom.dic</entry>
        </properties>
        """)
    with open(xml_path, "w", encoding="utf-8") as f:
        f.write(xml_content)

    # custom.dic（已存在也覆盖，确保和配置一致）
    dic_path = os.path.join(ik_config_dir, "custom.dic")
    words = cfg.get("es_ik_custom_words", [])
    with open(dic_path, "w", encoding="utf-8") as f:
        f.write("\n".join(words) + "\n")


def start_compose(script_dir: str, cfg: dict, skip_existing: bool = False) -> tuple[bool, str]:
    """启动 docker compose。

    如遇 pull 失败（403 Forbidden），可能是 Clash TUN 劫持了 Docker 代理。
    -k 模式：逐个服务启动，跳过已有容器的服务。
    """
    compose_path = os.path.join(script_dir, "docker-compose.yml")

    if not skip_existing:
        code, stdout, stderr = shell(f"docker compose -f {compose_path} up -d 2>&1")
        if code == 0:
            return True, "容器启动成功"
        combined = (stdout + stderr).lower()
        if "conflict" in combined or "already in use" in combined:
            for line in (stdout + stderr).split(chr(10)):
                if "already in use" in line:
                    name = line.split('"')[1] if '"' in line else "unknown"
                    return False, f"容器 {name} 已存在，请 docker rm -f {name} 后重试，或用 -k 跳过"
            return False, "容器名冲突，请手动处理或用 -k 跳过"
        print("  hint: 如 pull 失败(403)，可能是 Clash TUN 劫持了代理，建议关闭 Clash TUN 后重试")
        return False, f"启动失败 (exit={code})"

    # -k 模式：获取服务列表，逐个启动
    _sc, _so, _ = shell(f"docker compose -f {compose_path} config --services 2>&1")
    services = [s.strip() for s in (_so or "").split(chr(10)) if s.strip()]
    if not services:
        code, _, _ = shell(f"docker compose -f {compose_path} up -d 2>&1")
        return code == 0, "容器启动完成"

    skipped, ok, fail = [], 0, 0
    for svc in services:
        _code, _out, _ = shell(f"docker compose -f {compose_path} up -d --no-deps {svc} 2>&1")
        combined = (_out or "").lower()
        if "conflict" in combined or "already in use" in combined:
            name = svc
            for _line in (_out or "").split(chr(10)):
                if "already in use" in _line and '"' in _line:
                    name = _line.split('"')[1]
            skipped.append(name)
        elif _code == 0:
            ok += 1
        else:
            fail += 1
            print(f"  [warn] {svc} 启动异常: {(_out or '')[:120]}")

    if skipped:
        return True, f"跳过 {len(skipped)} 个已有容器 ({', '.join(skipped[:3])}...)" if len(skipped) > 3 else f"跳过已有容器: {', '.join(skipped)}"
    if fail == 0:
        return True, "容器启动成功"
    return False, f"部分容器启动失败 ({fail}/{ok + fail})"


def run_setup(quiet: bool, skip_existing: bool = False) -> tuple[int, int]:
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
    for d in [cfg["pg_data_dir"], cfg["pg_init_dir"], cfg["redis_data_dir"], cfg["kafka_data_dir"], cfg["es_config_dir"], cfg["es_data_dir"], cfg["es_plugins_dir"],
              cfg["nginx_html_dir"], cfg["nginx_conf_dir"], cfg["nginx_log_dir"],
              cfg["jenkins_data_dir"],
              cfg["jenkins_data_dir"] + "/workspace"]:
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

    # 生成 ES 配置
    ok, msg = generate_es_config(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 生成 Nginx 配置
    ok, msg = generate_nginx_conf(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 安装 IK 分词
    ok, msg = install_es_ik(script_dir, cfg)
    _print_result(quiet, ok, msg)
    passed, failed = _count(passed, failed, ok)

    # 启动容器
    ok, msg = start_compose(script_dir, cfg, skip_existing)
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
    skip_existing = "-k" in sys.argv or "--skip-existing" in sys.argv

    checks = [
        ("Java", check_java),
        ("Docker", check_docker_installed),
        ("Docker Compose", check_docker_compose),
        ("Docker 运行中", check_docker_running),
        ("Nacos 镜像", check_nacos_image),
        ("Maven", check_maven),
        ("Docker 组", check_docker_gid),
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
        sp, sf = run_setup(quiet, skip_existing)
        passed += sp
        failed += sf

    if not quiet:
        print()
        print("=" * 40)
        print(f"  结果: {passed}/{passed + failed} 通过")
        print("=" * 40)
        if failed == 0:
            cfg = get_config()
            h = cfg["host_ip"]
            print()
            print("  访问方式（宿主机 IP）:")
            print(f"  PostgreSQL: {h}:{cfg['pg_port']}  用户 {cfg['pg_user']}/{cfg['pg_password']}")
            print(f"  Nacos:      http://{h}:8080  (nacos/nacos)")
            print(f"  Redis:      {h}:{cfg['redis_port']}")
            print(f"  Kafka:      {h}:{cfg['kafka_port']}")
            print(f"  Kafka-UI:   http://{h}:{cfg['kafka_ui_port']}")
            print(f"  ES:         http://{h}:{cfg['es_port']}  (已装 IK 分词)")
            print(f"    词典路径:  data/elasticsearch/plugins/analysis-ik/config/custom.dic")
            print(f"  Nginx:      http://{h}  (前端静态 + 反代 Gateway)")
            print(f"  Kibana:     http://{h}:{cfg['kibana_port']}")
            print(f"  Jenkins:    http://{h}:{cfg['jenkins_port']}")

    sys.exit(0 if failed == 0 else 1)


if __name__ == "__main__":
    main()
