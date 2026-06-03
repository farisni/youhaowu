# TODO

> 基于 gulimall-dev（youhaowu-2022）对标 youhaowu 的功能差距分析。
> 标记 ✅ 的模块表示已完整迁移（含核心业务逻辑）。

---

## 🔴 跨服务调用 — 全部占位

所有 RemoteService 当前为 `return R.fail("占位")` 或返回假数据，需改为 WebClient 调用。

| 调用方 | RemoteService | 方法 | 被调方 |
|--------|--------------|------|--------|
| product | CouponRemoteService | saveSpuBounds / saveSkuReduction | coupon |
| product | WareRemoteService | getSkuHasStock | ware |
| product | SearchRemoteService | productStatusUp | search |
| order | WareRemoteService | lockStock / unlockStock | ware |
| order | ProductRemoteService | getSpuInfo / getSkuInfo | product |
| order | CartRemoteService | getUserCartItems | cart |
| order | MemberRemoteService | getMemberInfo | member |
| cart | ProductRemoteService | getInfo / getSkuSaleAttrValues / getPrice | product |
| seckill | ProductRemoteService | getSkuInfo / getSkuInfos | product |
| seckill | CouponRemoteService | getLates3DaySession | coupon |
| search | ProductRemoteService | getSkuInfos | product |
| member | CouponRemoteService | getMemberCoupons | coupon |

---

## 🔴 核心业务逻辑空壳

### youhaowu-order（订单模块）

OrderServiceImpl 当前仅 82 行纯 CRUD，缺以下核心方法（gulimall 原始 469 行）：

- [ ] **submitOrder** — 提交订单（验令牌、验价、锁库存、生成订单、删购物车）
- [ ] **getOrderConfirmData** — 订单确认页数据（地址、购物项、运费、总价）
- [ ] **closeOrder** — 关闭过期订单（超时未支付自动取消）
- [ ] **订单状态流转** — 待付款→已支付→已发货→已完成→已取消
- [ ] **支付回调** — handlePayResult（支付宝/微信回调处理）
- [ ] **秒杀订单消费** — Kafka 消费 seckill-order-topic 创建订单
- [ ] RemoteService 全部占位 → 见跨服务调用章节

### youhaowu-ware（仓储模块）

- [ ] **WareSkuServiceImpl.lockStock** — 下单锁库存（SKU 级分布式锁，gulimall 210 行）
- [ ] **WareSkuServiceImpl.unlockStock** — 订单取消/超时解锁库存
- [ ] RemoteService 全部占位 → 见跨服务调用章节

### youhaowu-search（搜索模块）

- [ ] **MallSearchServiceImpl.search()** — ES DSL 查询（多条件筛选、分页、聚合、品牌/属性/分类导航）
- [ ] **ProductSaveServiceImpl.productStatusUp()** — ES 批量索引写入（BulkRequest）
- [ ] RemoteService 全部占位 → 见跨服务调用章节
- [ ] ES 索引 mapping + 中文分词器（IK）

### youhaowu-coupon（优惠券模块）

- [ ] **SeckillSessionServiceImpl.getLates3DaySession()** — 查询最近三天秒杀场次（seckill 定时上架依赖）
- [ ] **SpuBoundsServiceImpl.saveSpuBounds** — SPU 积分保存业务逻辑
- [ ] **SkuFullReductionServiceImpl** — 满减 + 会员价批量保存（依赖 SkuLadder / MemberPrice）

---

## ⚠️ 有 Entity/Service 但缺 Controller 端点

### youhaowu-product

| 缺失 Controller | 对应 Entity | 说明 |
|-----------------|-------------|------|
| SkuImagesController | SkuImagesEntity | SKU 图片 REST API |
| SkuSaleAttrValueController | SkuSaleAttrValueEntity | SKU 销售属性 REST API |
| SpuCommentController | SpuCommentEntity | 商品评价 REST API |
| SpuImagesController | SpuImagesEntity | SPU 图片集 REST API |
| SpuInfoDescController | SpuInfoDescEntity | SPU 描述 REST API |

---

## 🔴 youhaowu-auth（认证模块）

gulimall 有 LoginController + OAuth2Controller，wheatmall 当前仅 DemoController。

> 注：member 模块已有 register / login / OAuth 端点。auth 的 Thymeleaf SSR 登录页面在 REST API 架构下是否需要保留待评估。

- [ ] **LoginController** — 登录页 / 注册 / 短信验证码（或评估 member 已覆盖后移除）
- [ ] **OAuth2Controller** — 微博 / Gitee 授权回调（或评估 member 已覆盖后移除）

---

## ✅ 已完整迁移模块

- ✅ **youhaowu-product** — 核心 CRUD + SpuInfoServiceImpl.saveBaseSpuInfo（106 行）+ 商品上架
- ✅ **youhaowu-coupon** — 16 组完整 CRUD
- ✅ **youhaowu-member** — 10 组 CRUD + 注册 / 密码登录 / Gitee OAuth
- ✅ **youhaowu-cart** — Redis 购物车（添加 / 合并 / 选中 / 数量 / 删除 / 结算）
- ✅ **youhaowu-seckill** — Redis 秒杀（定时上架 + 信号量 + Kafka 下单）
- ✅ **youhaowu-gateway** — 路由 + CORS + Nacos 发现

---

## 安全

- [x] 密码加密使用 BCrypt
- [ ] JWT 密钥通过环境变量注入
- [ ] 前端 Token 存储 httpOnly cookie 或内存
- [ ] 生产环境强制 HTTPS
- [ ] 登录失败次数限制

## 性能

- [ ] 用户权限缓存到 Redis
- [ ] Token 解析优化（JWT 缓存）
- [ ] username、role_code、perm_code 字段加索引

## 扩展

- [ ] Nacos Config 统一配置中心
- [ ] 单点登录 SSO
- [ ] 登录审计日志
- [ ] 多租户支持
- [ ] 重构 youhaowu：参照 springcloud-demo 的 plug 方式，抽取 youhaowu-plug 公共底座模块（MyBatis-Plus/Redisson/Kafka/Nacos），各业务模块只保留领域代码
---

## 基础设施

### 认证方案迁移
- [ ] Gateway + JWT Token 替代各模块 Spring Session（当前占位用 X-User-Id Header）

### Docker / 部署
- [ ] docker-compose.yml 补充 PostgreSQL、ES、Nacos、Kafka 完整编排
- [ ] 各模块 Dockerfile
- feat: 分类树接口加缓存（Redis/本地），减少 DB 查询
- refactor: 后端统一走 save 接口，id 空=新增，id 非空=更新，去除冗余 update 接口
- fix: 排查各 Entity @TableId 策略，统一改为 AUTO 自增（避免 Snowflake 与序列冲突）
- feat: MinIO 上传加 MD5 去重，避免重复文件占用存储
- refactor: Banner/News 图片 URL 存相对路径，返回时拼完整 URL（同品牌模块）
- feat: PostgreSQL 数据库定时备份（pg_dump + cron）
- feat: 全链路日志追踪（TraceId 贯穿 Gateway → 各微服务 → DB）
- refactor: 后端 save 方法统一改名为 saveOrUpdate，语义更明确（id 空=新增，id 非空=更新）
- feat: 前端首页内容缓存优化（banner/news/recommend/rank 列表加 Redis 缓存，减少 DB 查询）
- refactor: Controller 统一返回对象（去除 R<?> 裸返回，全部显式指定泛型类型）
- refactor: 商品上架流程改为 WebFlux 非阻塞写法（Mono 链式编排，去除 .block()）
