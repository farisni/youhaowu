# 认证服务拆分方案

## 服务划分

- **wheatmall-auth-customer**: 手机号/验证码登录，C端买家使用
- **wheatmall-auth-admin**: 用户名/密码+验证码，平台员工使用

## 核心区别

| 维度 | wheatmall-auth-customer | wheatmall-auth-admin |
|------|----------|-------|
| 认证方式 | 手机+验证码 | 账号+密码+验证码 |
| 权限模型 | 无 | RBAC |
| 安全级别 | 中 | 高 |

## 数据库

- customer_user (phone, status)
- admin_user (username, password) + admin_role + admin_permission

## 实现顺序

1. wheatmall-auth-customer（最简单，用户最多）
2. wheatmall-auth-admin（权限复杂c ）

## 技术选型

- Token: JWT + Redis
- 密码: BCrypt
- 权限: Spring Security
