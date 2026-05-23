# WheatMall Auth Admin 模块

## 模块简介

**wheatmall-auth-admin** 是 WheatMall 商城系统的认证授权管理模块，提供统一的用户认证、权限管理和安全控制功能。

## 核心功能

- **用户认证**：登录、登出、Token刷新
- **JWT管理**：Token签发、验证、黑名单
- **RBAC权限**：基于角色的访问控制
- **安全管理**：Spring Security + JWT 双保险

## 技术栈

- Spring Boot 3.4.2
- Spring Security 6.x
- JWT (JJWT)
- Redis (Token黑名单)
- Nacos 服务注册

## 服务信息

- **服务名**：wheatmall-auth-admin
- **端口**：8092
- **注册中心**：Nacos

## 目录结构

```
wheatmall-auth-admin/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/wheatmall/authadmin/
│   │   │       ├── AuthAdminApplication.java    # 启动类
│   │   │       ├── config/                     # 配置类
│   │   │       ├── controller/                 # 控制器
│   │   │       ├── service/                    # 业务逻辑
│   │   │       ├── security/                   # Security相关
│   │   │       └── util/                       # 工具类
│   │   └── resources/
│   │       └── application.yml                 # 配置文件
│   └── test/                                   # 测试代码
├── doc/                                        # 开发文档
└── pom.xml                                     # Maven配置
```

## 快速开始

### 1. 启动服务

```bash
cd backend/wheatmall-auth-admin
mvn spring-boot:run
```

### 2. 测试接口

服务启动后，可通过以下地址访问：
- 服务地址：http://localhost:8092
- Nacos控制台：http://127.0.0.1:8080/nacos

## 开发文档

详细开发计划见 `doc/` 目录：
- [JWT认证系统开发规划](./doc/JWT_AUTH_DEVELOPMENT_PLAN.md)

## 集成说明

其他服务集成认证模块：

1. 添加依赖：
```xml
<dependency>
    <groupId>com.wheatmall.common</groupId>
    <artifactId>wheatmall-common</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 引入JWT过滤器（配置中启用）

3. 使用权限注解：
```java
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAuthority('user:create')")
```
