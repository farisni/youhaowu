#!/usr/bin/env python3
"""Java 21 + Maven + Docker 一键安装/卸载脚本。

用法:
    python3 java-env-prepare.py               # 安装 java21 + maven + docker
    python3 java-env-prepare.py -u java       # 卸载 java
    python3 java-env-prepare.py -u maven      # 卸载 maven
    python3 java-env-prepare.py -u docker     # 卸载 docker
    python3 java-env-prepare.py -u sdkman    # 卸载 SDKMAN
    python3 java-env-prepare.py -u            # 卸载全部
    python3 java-env-prepare.py -q            # 静默模式
"""

import os
import subprocess
import sys
import textwrap


# ═══════════════════════════════════════════════════════════
#  常量
# ═══════════════════════════════════════════════════════════

JAVA_CANDIDATE = "21.0.6-tem"
MAVEN_CANDIDATE = "3.9.9"

JDK_MIRROR_URLS = [
    "https://ghproxy.net/https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.6%2B7/OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.6_7.tar.gz",
]
JDK_ARCHIVE_NAME = "temurin-21.0.6-linux-aarch64.tar.gz"

DOCKER_MIRRORS = [
    "https://docker.xuanyuan.me",
    "https://docker.m.daocloud.io",
]


# ═══════════════════════════════════════════════════════════
#  工具
# ═══════════════════════════════════════════════════════════

def shell(cmd: str, timeout: int = 120) -> tuple[int, str, str]:
    result = subprocess.run(
        cmd, shell=True, capture_output=True, text=True,
        timeout=timeout, executable="/bin/bash"
    )
    return result.returncode, result.stdout, result.stderr


def shell_live(cmd: str):
    return os.system(cmd)


def sdk_exec(cmd: str) -> int:
    """在 bash 中 source sdkman-init.sh 后执行 sdk 命令。"""
    init = os.path.expanduser("~/.sdkman/bin/sdkman-init.sh")
    if not os.path.exists(init):
        return 1
    return shell_live(f'bash -c \'source "{init}" && {cmd}\'')


# ═══════════════════════════════════════════════════════════
#  检查
# ═══════════════════════════════════════════════════════════

def check_sdkman() -> tuple[bool, str]:
    home = os.path.expanduser("~/.sdkman")
    if os.path.isdir(home):
        return True, "SDKMAN 已安装"
    return False, "SDKMAN 未安装"


def check_java() -> tuple[bool, str]:
    code, stdout, _ = shell("java -version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Java: {ver}"
    return False, "Java 未安装"


def check_maven() -> tuple[bool, str]:
    code, stdout, _ = shell("mvn --version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Maven: {ver}"
    return False, "Maven 未安装"


def check_docker() -> tuple[bool, str]:
    code, stdout, _ = shell("docker --version 2>&1")
    if code == 0:
        return True, f"Docker: {stdout.strip()}"
    return False, "Docker 未安装"


# ═══════════════════════════════════════════════════════════
#  安装
# ═══════════════════════════════════════════════════════════

def install_sdkman() -> bool:
    """安装 SDKMAN。"""
    print("  → curl -sL --http1.1 \"https://get.sdkman.io\" | bash")
    rc = shell_live('curl -sL --http1.1 "https://get.sdkman.io" | bash')
    if rc != 0:
        print("  重试...")
        rc = shell_live('curl -sL --http1.1 "https://get.sdkman.io" | bash')
    return rc == 0


def _download_jdk_offline() -> bool:
    """从国内镜像下载 JDK 到 SDKMAN 缓存，避免慢速外网下载。"""
    archives = os.path.expanduser("~/.sdkman/archives")
    os.makedirs(archives, exist_ok=True)
    dest = os.path.join(archives, JDK_ARCHIVE_NAME)
    if os.path.exists(dest) and os.path.getsize(dest) > 50_000_000:
        return True
    if os.path.exists(dest):
        os.remove(dest)  # 删除损坏/不完整的缓存

    for url in JDK_MIRROR_URLS:
        print(f"  → 下载 JDK（国内镜像）...")
        rc = shell_live(f"curl -L --http1.1 --connect-timeout 10 --max-time 300 -o {dest} '{url}'")
        if rc == 0 and os.path.getsize(dest) > 10_000_000:
            return True
        print("  → 重试下一个...")
    return False


def install_via_sdk(candidate: str, version: str) -> bool:
    """通过 sdk 安装指定工具。"""
    print(f"  → sdk install {candidate} {version} ...")
    rc = sdk_exec(f"sdk install {candidate} {version}")
    if rc != 0:
        return False
    sdk_exec(f"sdk default {candidate} {version}")
    return True


def install_docker() -> bool:
    """安装 Docker CE。"""
    repo_file = "/etc/yum.repos.d/docker-ce.repo"
    if not os.path.exists(repo_file):
        print("  → 写入阿里云 Docker CE 仓库 ...")
        repo = textwrap.dedent("""\
            [docker-ce-stable]
            name=Docker CE Stable - Aliyun
            baseurl=https://mirrors.aliyun.com/docker-ce/linux/centos/$releasever/$basearch/stable
            enabled=1
            gpgcheck=1
            gpgkey=https://mirrors.aliyun.com/docker-ce/linux/centos/gpg
            """)
        shell_live(f"sudo tee {repo_file} << 'REPO_EOF'\n{repo}\nREPO_EOF")
    return shell_live("sudo yum install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin 2>&1") == 0


def configure_docker_mirror():
    """配置 Docker 镜像加速。"""
    daemon = "/etc/docker/daemon.json"
    import json
    try:
        with open(daemon) as fh:
            cfg = json.load(fh)
    except Exception:
        cfg = {}
    cfg["registry-mirrors"] = DOCKER_MIRRORS
    tmp = "/tmp/daemon.json"
    with open(tmp, "w") as fh:
        json.dump(cfg, fh, indent=2)
    shell_live(f"sudo mkdir -p /etc/docker && sudo cp {tmp} {daemon}")


def generate_maven_settings():
    """生成 ~/.m2/settings.xml（阿里云镜像）。"""
    m2_dir = os.path.expanduser("~/.m2")
    os.makedirs(m2_dir, exist_ok=True)
    conf = textwrap.dedent(f"""\
        <settings>
          <mirrors>
            <mirror>
              <id>aliyunmaven</id>
              <mirrorOf>*</mirrorOf>
              <name>阿里云公共仓库</name>
              <url>https://maven.aliyun.com/repository/public</url>
            </mirror>
          </mirrors>
        </settings>
        """)
    with open(os.path.join(m2_dir, "settings.xml"), "w") as f:
        f.write(conf)


# ═══════════════════════════════════════════════════════════
#  卸载
# ═══════════════════════════════════════════════════════════

def uninstall_sdkman():
    shell_live("rm -rf ~/.sdkman")
    shell_live("sed -i '/sdkman/d' ~/.bashrc ~/.zshrc 2>/dev/null")


def uninstall_java():
    sdk_dir = os.path.expanduser("~/.sdkman/candidates/java")
    if os.path.exists(sdk_dir):
        shell_live(f"rm -rf {sdk_dir}")


def uninstall_maven():
    sdk_dir = os.path.expanduser("~/.sdkman/candidates/maven")
    if os.path.exists(sdk_dir):
        shell_live(f"rm -rf {sdk_dir}")


def uninstall_docker():
    shell_live("sudo systemctl stop docker 2>/dev/null")
    shell_live("sudo yum remove -y docker-ce docker-ce-cli containerd.io docker-compose-plugin 2>&1")


# ═══════════════════════════════════════════════════════════
#  主流程
# ═══════════════════════════════════════════════════════════

def main():
    quiet = "-q" in sys.argv or "--quiet" in sys.argv
    uninstall = "-u" in sys.argv or "--uninstall" in sys.argv

    if uninstall:
        target = "all"
        for a in sys.argv:
            if a in ("java", "maven", "docker", "sdkman", "sdk", "all"):
                target = a
                break
        if not quiet:
            print("=" * 40)
            print(f"  卸载 {target}")
            print("=" * 40)
        if target in ("sdkman", "sdk", "all"):
            uninstall_sdkman()
        if target in ("java", "all"):
            uninstall_java()
        if target in ("maven", "all"):
            uninstall_maven()
        if target in ("docker", "all"):
            uninstall_docker()
        if not quiet:
            print(f"\n[✓] 卸载 {target} 完成")
        return

    if not quiet:
        print("=" * 40)
        print("  开发环境准备")
        print("=" * 40)

    # 1️⃣ SDKMAN
    ok, msg = check_sdkman()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not install_sdkman():
            print("[✗] SDKMAN 安装失败，请检查网络")
            sys.exit(1)
        if not quiet:
            print("[✓] SDKMAN 安装完成")

    # 2️⃣ Java（优先国内镜像离线安装）
    ok, msg = check_java()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u java)")
    else:
        _download_jdk_offline()  # 失败不阻塞，回退到 sdk 在线安装
        if not install_via_sdk("java", JAVA_CANDIDATE):
            print("[✗] Java 安装失败")
            sys.exit(1)
        if not quiet:
            print(f"[✓] Java {JAVA_CANDIDATE} 安装完成")
            print("        (如需卸载: python3 java-env-prepare.py -u java)")

    # 3️⃣ Maven
    ok, msg = check_maven()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u maven)")
    else:
        if not install_via_sdk("maven", MAVEN_CANDIDATE):
            print("[✗] Maven 安装失败")
            sys.exit(1)
        generate_maven_settings()
        if not quiet:
            print(f"[✓] Maven {MAVEN_CANDIDATE} 安装完成")
            print("        (如需卸载: python3 java-env-prepare.py -u maven)")

    # 4️⃣ Docker
    ok, msg = check_docker()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u docker)")
    else:
        if not install_docker():
            print("[✗] Docker 安装失败")
            sys.exit(1)
        configure_docker_mirror()
        shell_live("sudo systemctl enable docker 2>&1")
        shell_live("sudo systemctl start docker 2>&1")
        if not quiet:
            print("[✓] Docker 安装完成（镜像加速已配置）")
            print("        (如需卸载: python3 java-env-prepare.py -u docker)")

    if not quiet:
        print()
        print("=" * 40)
        print("  完成")
        print("  source ~/.sdkman/bin/sdkman-init.sh 后生效")
        print("=" * 40)


if __name__ == "__main__":
    main()
