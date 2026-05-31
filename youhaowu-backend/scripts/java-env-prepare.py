#!/usr/bin/env python3
"""SDKMAN + Java 21 + Maven 一键安装脚本。

用法:
    python3 java-env-prepare.py          # 安装 sdk + java21 + maven
    python3 java-env-prepare.py -q       # 静默模式
"""

import os
import subprocess
import sys
import platform
import textwrap

SDKMAN_URL = "https://get.sdkman.io"
JAVA_VERSION = "21.0.6-tem"
MAVEN_VERSION = "3.9.9"


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


# ═══════════════════════════════════════════════════════════
#  检查
# ═══════════════════════════════════════════════════════════

def check_sdkman() -> tuple[bool, str]:
    """检查 SDKMAN 是否已安装。"""
    home = os.path.expanduser("~/.sdkman")
    if os.path.isdir(home):
        return True, "SDKMAN 已安装"
    return False, "SDKMAN 未安装"


def check_java() -> tuple[bool, str]:
    """检查 Java 版本。"""
    code, stdout, _ = shell("java -version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Java: {ver}"
    return False, "Java 未安装"


def check_maven() -> tuple[bool, str]:
    """检查 Maven。"""
    code, stdout, _ = shell("mvn --version 2>&1")
    if code == 0:
        ver = stdout.split("\n")[0] if stdout else "unknown"
        return True, f"Maven: {ver}"
    return False, "Maven 未安装"


def check_maven_settings() -> tuple[bool, str]:
    """检查 ~/.m2/settings.xml 是否存在。"""
    path = os.path.expanduser("~/.m2/settings.xml")
    if os.path.exists(path):
        return True, "Maven settings.xml 已存在"
    return False, "Maven settings.xml 未配置"


def generate_maven_settings():
    """生成 ~/.m2/settings.xml（阿里云/华为云/腾讯云镜像）。"""
    m2_dir = os.path.expanduser("~/.m2")
    os.makedirs(m2_dir, exist_ok=True)
    conf = textwrap.dedent("""\
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
#  安装
# ═══════════════════════════════════════════════════════════

def install_sdkman() -> bool:
    """安装 SDKMAN。"""
    print("  → curl -s \"https://get.sdkman.io\" | bash")
    rc = shell_live('curl -s "https://get.sdkman.io" | bash')
    return rc == 0


def install_via_sdk(candidate: str, version: str) -> bool:
    """通过 sdk 安装指定候选工具。"""
    sdkman_init = os.path.expanduser("~/.sdkman/bin/sdkman-init.sh")
    cmd = f'source "{sdkman_init}" && sdk install {candidate} {version}'
    rc = shell_live(f'bash -c \'{cmd}\'')
    return rc == 0


def set_default(candidate: str, version: str) -> bool:
    """设置默认版本。"""
    sdkman_init = os.path.expanduser("~/.sdkman/bin/sdkman-init.sh")
    cmd = f'source "{sdkman_init}" && sdk default {candidate} {version}'
    rc = shell_live(f'bash -c \'{cmd}\'')
    return rc == 0


# ═══════════════════════════════════════════════════════════
#  主流程
# ═══════════════════════════════════════════════════════════

def main():
    quiet = "-q" in sys.argv or "--quiet" in sys.argv

    if not quiet:
        print("=" * 40)
        print("  开发环境准备")
        print("=" * 40)

    # 1️⃣ SDKMAN
    ok, msg = check_sdkman()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not quiet:
            print("  安装 SDKMAN...")
        if not install_sdkman():
            print("[✗] SDKMAN 安装失败")
            sys.exit(1)
        if not quiet:
            print("[✓] SDKMAN 安装完成")

    # 2️⃣ Java 21
    ok, msg = check_java()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not quiet:
            print(f"  通过 sdk 安装 Java {JAVA_VERSION} ...")
        if not install_via_sdk("java", JAVA_VERSION):
            print("[✗] Java 安装失败")
            sys.exit(1)
        set_default("java", JAVA_VERSION)
        if not quiet:
            print(f"[✓] Java {JAVA_VERSION} 安装完成")

    # 3️⃣ Maven
    ok, msg = check_maven()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not quiet:
            print(f"  通过 sdk 安装 Maven {MAVEN_VERSION} ...")
        if not install_via_sdk("maven", MAVEN_VERSION):
            print("[✗] Maven 安装失败")
            sys.exit(1)
        set_default("maven", MAVEN_VERSION)
        if not quiet:
            print(f"[✓] Maven {MAVEN_VERSION} 安装完成")

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
        print("  完成！新终端生效：source ~/.sdkman/bin/sdkman-init.sh")
        print("=" * 40)


if __name__ == "__main__":
    main()
