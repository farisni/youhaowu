# TODO

## 安全

- [x] 密码加密使用 BCrypt（SecurityConfig 已配置 BCryptPasswordEncoder）
- [ ] JWT 密钥通过环境变量注入（当前 `@Value` 有硬编码默认值）
- [ ] 前端 Token 存储 httpOnly cookie 或内存，避免 XSS
- [ ] 生产环境强制 HTTPS
- [ ] 登录失败次数限制（防暴力破解）

## 认证

- [x] Token 续期（refreshToken 机制已实现）
- [x] Spring Security + JWT 无状态认证

## 性能

- [ ] 用户权限缓存到 Redis
- [ ] Token 解析优化（JWT 缓存 / 本地线程缓存）
- [ ] username、role_code、perm_code 字段加索引

## 扩展

- [ ] 多租户支持（添加 tenant_id）
- [ ] OAuth2.0 第三方登录（微信、QQ、GitHub）
- [ ] 单点登录 SSO（CAS / OAuth2）
- [ ] 登录审计日志（IP、时间、设备）
- [ ] Nacos Config 统一配置中心

## Search 模块

- [ ] MallSearchServiceImpl.search() — ES 检索逻辑（DSL 构建、分页、聚合、品牌/属性/分类导航）
- [ ] ProductSaveServiceImpl.productStatusUp() — ES 索引批量写入（BulkRequest）
- [ ] ProductRemoteService — WebClient 调用 Product 模块（替换 Feign 占位）
- [ ] ElasticSaveController.productStatusUp() — 错误处理完善（BizCodeEnum + 日志告警）
- [ ] SearchController — Thymeleaf 页面搜索（需 Spring Session + Redis + 前端模板）
- [ ] ES 索引 mapping 定义 + 中文分词器（IK）配置
