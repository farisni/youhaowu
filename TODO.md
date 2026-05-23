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

---

## 未迁移模块（对标 wheatmall-2022）

### wheatmall-member（用户模块）⚠️ 高优先级
2022 有 11 组完整 CRUD + 注册登录，2026 完全缺失

- [ ] 10 Entity + 10 Mapper + 10 Service + 10 Controller（Member/Level/Address/LoginLog/CollectSpu/CollectSubject 等）
- [ ] MemberService — 注册、登录（普通+社交）、手机号唯一校验
- [ ] MemberReceiveAddressService — 用户收货地址
- [ ] CouponRemoteService — WebClient 调用 Coupon 模块（替换 Feign）
- [ ] VO: MemberUserRegisterVO / SocialUser / GiteeUser
- [ ] exception: PhoneException / UsernameException
- [ ] application.yml + DB: wheatmall_ums

### wheatmall-gateway（网关）
- [ ] Spring Cloud Gateway 路由配置
- [ ] CORS 跨域配置
- [ ] 统一前缀 /api 路由转发

### renren-fast（管理后台）
- [ ] 原 2022 依赖 renren-fast 作为后台管理，2026 用 wheatmall-auth 替代
- [ ] 若需代码生成器：renren-generator

---

## 各模块核心逻辑缺口

### wheatmall-product
- [ ] SpuInfoService.saveBaseSpuInfo — 保存 SPU 同时调用 Coupon 保存满减/积分（跨模块调用）
- [ ] ProductAttrValueService — 基本属性批量保存（baseSaleAttrs）
- [ ] SkuInfoService — SKU 图片批量保存
- [ ] CouponRemoteService / WareRemoteService / SearchRemoteService — WebClient 实现（当前为占位）
- [ ] ProductConstant — 商品状态常量（SPU_UP/NEW/DOWN）
- [ ] SkuEsModel / SpuBoundTo — TO 类迁移到 common

### wheatmall-order
- [ ] OrderService — 创建订单（验令牌、验价、锁库存）、订单确认页数据
- [ ] OrderItemService — 订单项批量保存
- [ ] 订单状态流转（待付款→已支付→已发货→已完成→已取消）
- [ ] WareRemoteService — WebClient 锁库存/解锁库存
- [ ] ProductRemoteService — WebClient 查 SPU/SKU 信息
- [ ] CartRemoteService / MemberRemoteService — WebClient 占位

### wheatmall-ware
- [ ] PurchaseService — 合并采购需求、领取采购单、完成采购
- [ ] WareSkuService — 库存锁定/解锁（订单扣库存核心逻辑）
- [ ] PurchaseDetailService — 采购需求状态流转
- [ ] WareConstant — 采购单状态常量
- [ ] SkuHasStockVo — 移入 common（已部分迁移）

### wheatmall-coupon
- [ ] SkuFullReductionService.saveSkuReduction — 满减+会员价批量保存逻辑（依赖 SkuLadder/MemberPrice）
- [ ] SeckillSessionService — 秒杀场次关联 SKU
- [ ] SeckillSkuRelationService — 秒杀商品关联
- [ ] SpuBoundsService — SPU 积分保存

### wheatmall-search
- [ ] MallSearchServiceImpl.search() — ES DSL 查询构建、分页、聚合、品牌/属性/分类导航
- [ ] ProductSaveServiceImpl.productStatusUp() — ES 批量索引（BulkRequest）
- [ ] ProductRemoteService — WebClient 实现（替换占位）
- [ ] SearchController — Thymeleaf 搜索页（需 Redis + Spring Session）
- [ ] ES 索引 mapping + 中文分词器（IK）

### wheatmall-thirdparty
- [ ] OSSController — 前端直传 OSS 签名（已有，需验证与前端联调）
- [ ] SmsSendController.sendCode — 短信验证码发送（已有，需验证）

---

## 前端

### wheatmall-admin（Vue2→Vue3 重写）
- [ ] 2022 为 Vue2 + Element UI，2026 已搭建 Vite + React? 框架
- [ ] 商品管理（SPU/SKU/品牌/分类/属性）
- [ ] 订单管理
- [ ] 优惠券管理
- [ ] 用户管理
- [ ] 仓储管理

---

## 基础设施

### 配置中心
- [ ] Nacos Config 统一配置管理（当前各模块本地 application.yml）
- [ ] 敏感信息（AK/SK、DB密码）从 Nacos 或环境变量注入

### Docker / 部署
- [ ] docker-compose.yml 补充 PostgreSQL、ES、Nacos、Kafka 完整编排
- [ ] 各模块 Dockerfile
