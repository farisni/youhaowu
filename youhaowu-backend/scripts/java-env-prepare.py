#!/usr/bin/env python3
"""Java 21 + Maven 一键安装/卸载脚本（yum 版，走阿里云镜像）。

用法:
    python3 java-env-prepare.py               # 安装 java21 + maven
    python3 java-env-prepare.py -u java       # 卸载 java
    python3 java-env-prepare.py -u maven      # 卸载 maven
    python3 java-env-prepare.py -u            # 卸载 java + maven + settings
    python3 java-env-prepare.py -q            # 静默模式
"""

import os
import subprocess
import sys
import textwrap


# ═══════════════════════════════════════════════════════════
#  常量
# ═══════════════════════════════════════════════════════════

JAVA_PKGS = "java-21-openjdk java-21-openjdk-devel"
JAVA_ALT = "java-21-openjdk.aarch64"
MAVEN_VER = "3.9.9"
MIN_MAVEN_VER = "3.9"

DOCKER_REPOS = [
    "https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo",
    "https://download.docker.com/linux/centos/docker-ce.repo",
]
DOCKER_MIRRORS = [
    "https://docker.xuanyuan.me",
    "https://docker.m.daocloud.io",
]
MAVEN_URLS = [
    "https://repo.huaweicloud.com/apache/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz",
    "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz",
]
MAVEN_HOME = "/opt/maven"
MIRROR_URL = "https://maven.aliyun.com/repository/public"
MIRROR_NAME = "阿里云公共仓库"


# ═══════════════════════════════════════════════════════════
#  工具
# ═══════════════════════════════════════════════════════════

def shell(cmd: str, timeout: int = 120) -> tuple[int, str, str]:
    """执行 shell 命令，返回 (returncode, stdout, stderr)。"""
    result = subprocess.run(
        cmd, shell=True, capture_output=True, text=True,
        timeout=timeout, executable="/bin/bash"
    )
    return result.returncode, result.stdout, result.stderr


def shell_live(cmd: str):
    """执行 shell 命令，实时输出到终端。"""
    return os.system(cmd)


def _is_root() -> bool:
    return os.geteuid() == 0


# ═══════════════════════════════════════════════════════════
#  检查
# ═══════════════════════════════════════════════════════════

def _setup_sudo():
    """配置当前用户 yum 免密 sudo。"""
    user = os.environ.get("USER", "unknown")
    sudoers_file = f"/etc/sudoers.d/{user}-yum"
    if os.path.exists(sudoers_file):
        return
    rule = f"{user} ALL=(ALL) NOPASSWD: /usr/bin/yum\n"
    code, _, _ = shell(f"echo '{rule}' | sudo tee {sudoers_file} 2>&1")
    if code != 0:
        print("  [warn] sudo 免密配置失败，可能需要手动输入密码")


def check_java() -> tuple[bool, str]:
    """检查 Java 是否已安装。"""
    code, stdout, _ = shell("java -version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Java: {ver}"
    return False, "Java 未安装"


def check_maven() -> tuple[bool, str]:
    """检查 Maven 是否已安装。"""
    code, stdout, _ = shell("mvn --version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Maven: {ver}"
    return False, "Maven 未安装"


def check_docker() -> tuple[bool, str]:
    """检查 Docker 是否已安装。"""
    code, stdout, _ = shell("docker --version 2>&1")
    if code == 0:
        ver = stdout.strip()
        return True, f"Docker: {ver}"
    return False, "Docker 未安装"


def check_docker_compose() -> tuple[bool, str]:
    """检查 Docker Compose 是否可用。"""
    code, stdout, _ = shell("docker compose version 2>&1")
    if code == 0:
        return True, f"Docker Compose: {stdout.strip()}"
    return False, "Docker Compose 未安装"


def check_docker_running() -> tuple[bool, str]:
    """检查 Docker 是否运行中。"""
    code, _, _ = shell("docker info 2>&1")
    if code == 0:
        return True, "Docker 运行中"
    return False, "Docker 未运行，请执行: sudo systemctl start docker"



def check_maven_settings() -> tuple[bool, str]:
    """检查 ~/.m2/settings.xml 是否存在。"""
    path = os.path.expanduser("~/.m2/settings.xml")
    if os.path.exists(path):
        return True, "Maven settings.xml 已存在"
    return False, "Maven settings.xml 未配置"


# ═══════════════════════════════════════════════════════════
#  安装
# ═══════════════════════════════════════════════════════════

def install_java() -> bool:
    """yum 安装 Java 21。"""
    print(f"  → sudo yum install -y {JAVA_PKGS}")
    return shell_live(f"sudo yum install -y {JAVA_PKGS} 2>&1") == 0


def install_maven() -> bool:
    """从 Apache 下载 Maven 到 /opt/maven 并配置 PATH。"""
    if os.path.exists(MAVEN_HOME):
        print(f"  → Maven 已存在 {MAVEN_HOME}")
        return True

    tarball = f"/tmp/maven-{MAVEN_VER}.tar.gz"
    print(f"  → 下载 Maven {MAVEN_VER} ...")
    rc = 1
    for url in MAVEN_URLS:
        rc = shell_live(f"curl -sfL --connect-timeout 10 --max-time 60 -o {tarball} '{url}'")
        if rc == 0:
            break
        print(f"  → 重试下一个镜像...")
    if rc != 0:
        print("  [✗] 下载失败，所有镜像不可达")
        return False

    print(f"  → 解压到 {MAVEN_HOME} ...")
    rc = shell_live(f"sudo mkdir -p /opt && sudo tar -xzf {tarball} -C /opt && sudo mv /opt/apache-maven-{MAVEN_VER} {MAVEN_HOME}")
    shell_live(f"rm -f {tarball}")
    if rc != 0:
        return False

    # 配置 PATH
    mvn_bin = f"{MAVEN_HOME}/bin"
    profile = os.path.expanduser("~/.bashrc")
    if not os.path.exists(profile):
        profile = os.path.expanduser("~/.bash_profile")
    marker = f"# MAVEN_HOME ({MAVEN_VER})"
    with open(profile, "r") as fh:
        if marker in fh.read():
            return True
    with open(profile, "a") as fh:
        fh.write(f"\n{marker}\nexport MAVEN_HOME={MAVEN_HOME}\nexport PATH=$MAVEN_HOME/bin:$PATH\n")
    print(f"  → PATH 已写入 {profile}")
    return True


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
              {MIRROR_NAME}
              {MIRROR_URL}
            </mirror>
          </mirrors>
        </settings>
        """)
    with open(os.path.join(m2_dir, "settings.xml"), "w") as f:
        f.write(conf)


#  卸载
# ═══════════════════════════════════════════════════════════

def uninstall_java():
    """卸载所有已安装的 Java 相关包。"""
    code, out, _ = shell("rpm -qa | grep -i java 2>/dev/null")
    if out.strip():
        print(f"  已安装: {', '.join(out.strip().split(chr(10)))}")
    else:
        print("  未检测到 Java 包")
        return
    print("  → sudo yum remove -y java-*-openjdk*")
    shell_live("sudo yum remove -y java-*-openjdk* tzdata-java javapackages-filesystem javapackages-tools 2>&1")


def uninstall_maven():
    """卸载 Maven（删除 /opt/maven 和 PATH 配置）。"""
    if os.path.exists(MAVEN_HOME):
        print(f"  → sudo rm -rf {MAVEN_HOME}")
        shell_live(f"sudo rm -rf {MAVEN_HOME}")

    # 清理 bashrc 中的 Maven 配置
    for prof in ["~/.bashrc", "~/.bash_profile"]:
        prof = os.path.expanduser(prof)
        if not os.path.exists(prof):
            continue
        with open(prof, "r") as fh:
            lines = fh.readlines()
        new_lines = [l for l in lines if "MAVEN_HOME" not in l or l.strip().startswith("#")]
        if len(new_lines) != len(lines):
            with open(prof, "w") as fh:
                fh.writelines(new_lines)
            print(f"  → 已清理 {prof}")


def uninstall_maven_settings():
    """删除 ~/.m2/settings.xml。"""
    path = os.path.expanduser("~/.m2/settings.xml")
    if os.path.exists(path):
        os.remove(path)
        print(f"  → 已删除 {path}")


def install_docker() -> bool:
    """安装 Docker CE + Compose。"""
    repo_file = "/etc/yum.repos.d/docker-ce.repo"
    if not os.path.exists(repo_file):
        print("  → 添加 Docker CE 仓库 ...")
        for repo_url in DOCKER_REPOS:
            rc = shell_live(f"sudo yum-config-manager --add-repo {repo_url} 2>&1")
            if rc == 0:
                break
            print(f"  → 重试下一个镜像...")
        if rc != 0:
            return False
    print("  → sudo yum install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin")
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


def uninstall_docker():
    """卸载 Docker。"""
    print("  → sudo yum remove -y docker-ce docker-ce-cli containerd.io docker-compose-plugin")
    shell_live("sudo yum remove -y docker-ce docker-ce-cli containerd.io docker-compose-plugin 2>&1")


# ═══════════════════════════════════════════════════════════
#  主流程
# ═══════════════════════════════════════════════════════════

def main():
    quiet = "-q" in sys.argv or "--quiet" in sys.argv
    uninstall = "-u" in sys.argv or "--uninstall" in sys.argv

    if uninstall:
        # 解析卸载目标
        target = "all"
        for a in sys.argv:
            if a in ("java", "maven", "docker", "settings", "all"):
                target = a
                break

        if not quiet:
            print("=" * 40)
            print(f"  卸载 {target}")
            print("=" * 40)

        if target in ("java", "all"):
            uninstall_java()
        if target in ("maven", "all"):
            uninstall_maven()
        if target in ("docker", "all"):
            uninstall_docker()
        if target in ("settings", "all"):
            uninstall_maven_settings()

        if not quiet:
            print()
            print(f"[✓] 卸载 {target} 完成")
        return

    if not quiet:
        print("=" * 40)
        print("  开发环境准备（yum）")
        print("=" * 40)

    # 0️⃣ sudo 免密
    _setup_sudo()

    # 1️⃣ Java 21
    ok, msg = check_java()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u java)")
    else:
        if not install_java():
            print("[✗] Java 安装失败")
            sys.exit(1)
        jdk_ver = shell("java -version 2>&1")[1]
        if "17." in jdk_ver:
            shell_live(f"sudo alternatives --set java {JAVA_ALT} 2>&1")
        if not quiet:
            ok2, _ = check_java()
            print("[✓] Java 安装完成（已设 21 为默认）")

    # 2️⃣ Maven
    ok, msg = check_maven()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u maven)")
    else:
        if not install_maven():
            print("[✗] Maven 安装失败")
            sys.exit(1)
        ver = shell("mvn --version 2>&1")[1]
        if ver and "3.9" not in ver:
            print(f"  [warn] Maven 版本低于 {MIN_MAVEN_VER}: {ver.split(chr(10))[0]}")
        if not quiet:
            print("[✓] Maven 安装完成")

    # 3️⃣ Docker
    ok, msg = check_docker()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if ok:
        if not quiet:
            print("        (如需卸载: python3 java-env-prepare.py -u docker)")
    if not ok:
        if not install_docker():
            print("[✗] Docker 安装失败")
            sys.exit(1)
        configure_docker_mirror()
        shell_live("sudo systemctl enable docker 2>&1")
        shell_live("sudo systemctl start docker 2>&1")
        if not quiet:
            print("[✓] Docker 安装完成（镜像加速已配置）")
            print("        (如需卸载: python3 java-env-prepare.py -u docker)")
            print("  → 验证: docker run hello-world ...")
            shell_live("docker run hello-world 2>&1")

    # 4️⃣ Maven settings.xml
    ok, msg = check_maven_settings()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        generate_maven_settings()
        if not quiet:
            print("[✓] ~/.m2/settings.xml 已生成（阿里云镜像）")

    if not quiet:
        print()
        print("=" * 40)
        print("  完成")
        print("  source ~/.bashrc 后 mvn --version 生效")
        print("  验证 Docker: docker run hello-world")
        print("=" * 40)


if __name__ == "__main__":
    main()
