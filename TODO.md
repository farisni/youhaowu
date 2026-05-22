# TODO

## 安全

- [ ] JWT 密钥通过环境变量或配置中心注入，不硬编码
- [ ] 密码加密使用 BCrypt 等强哈希算法
- [ ] 前端 Token 存储 httpOnly cookie 或内存，避免 XSS
- [ ] 生产环境强制 HTTPS
- [ ] 登录失败次数限制（防暴力破解）

## 认证

- [ ] 无感刷新 Token 续期机制

## 性能

- [ ] 用户权限缓存到 Redis，减少数据库查询
- [ ] Token 解析优化（JWT 缓存 / 本地线程缓存）
- [ ] username、role_code、perm_code 字段加索引

## 扩展

- [ ] 多租户支持（添加 tenant_id）
- [ ] OAuth2.0 第三方登录（微信、QQ、GitHub）
- [ ] 单点登录 SSO（CAS / OAuth2）
- [ ] 登录审计日志（IP、时间、设备）
- [ ] Nacos Config 统一配置中心
