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
    """卸载 Maven。"""
    print("  → sudo yum remove -y maven")
    shell_live("sudo yum remove -y maven 2>&1")


def uninstall_maven_settings():
    """删除 ~/.m2/settings.xml。"""
    path = os.path.expanduser("~/.m2/settings.xml")
    if os.path.exists(path):
        os.remove(path)
        print(f"  → 已删除 {path}")


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
            if a in ("java", "maven", "settings", "all"):
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
            shell_live("sudo alternatives --set java java-21-openjdk.aarch64 2>&1")
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
