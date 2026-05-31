#!/usr/bin/env python3
"""Java 21 + Maven 一键安装脚本（yum 版，走阿里云镜像）。

用法:
    python3 java-env-prepare.py          # 安装 java21 + maven + settings.xml
    python3 java-env-prepare.py -q       # 静默模式
"""

import os
import subprocess
import sys
import textwrap


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
    print("  → sudo yum install -y java-21-openjdk java-21-openjdk-devel")
    return shell_live("sudo yum install -y java-21-openjdk java-21-openjdk-devel 2>&1") == 0


def install_maven() -> bool:
    """yum 安装 Maven。"""
    print("  → sudo yum install -y maven")
    return shell_live("sudo yum install -y maven 2>&1") == 0


def generate_maven_settings():
    """生成 ~/.m2/settings.xml（阿里云镜像）。"""
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
#  主流程
# ═══════════════════════════════════════════════════════════

def main():
    quiet = "-q" in sys.argv or "--quiet" in sys.argv

    if not quiet:
        print("=" * 40)
        print("  开发环境准备（yum）")
        print("=" * 40)

    # 1️⃣ Java 21
    ok, msg = check_java()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not install_java():
            print("[✗] Java 安装失败")
            sys.exit(1)
        if not quiet:
            ok2, _ = check_java()
            print(f"[✓] Java 安装完成")

    # 2️⃣ Maven
    ok, msg = check_maven()
    if not quiet:
        print(f"[{'✓' if ok else '✗'}] {msg}")
    if not ok:
        if not install_maven():
            print("[✗] Maven 安装失败")
            sys.exit(1)
        if not quiet:
            print("[✓] Maven 安装完成")

    # 3️⃣ Maven settings.xml
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
        print("=" * 40)


if __name__ == "__main__":
    main()
