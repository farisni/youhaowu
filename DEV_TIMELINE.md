# 开发时间线 

## 2026-02-16

- feat: 项目初始化，创建 wheatmall-2026 仓库
- feat: 实现 Order 模块通过 WebClient 调用 Product 模块（跨模块调用 Demo）
- refactor: 使用常量类 ServiceUris 统一管理 URI 路径

## 2026-02-17

- feat: 集成 Nacos 3.1.1 服务注册发现
- feat: 添加通用响应结果类 R（泛型版本）
- refactor: ProductController 接口统一返回 R 对象
- feat: 整合 Mockito + JUnit 5 单元测试框架
- feat: 为 order 模块添加单元测试
- docs: 更新 AI 操作规则文档（测试验证、Git 提交、文档记录、需求拆分规则）
- docs: 添加 Java 对象命名规范
- feat: 创建 wheatmall-auth-admin 认证模块框架
- feat: 添加 JWT 工具类和假数据模型
- feat: 添加 AuthService 和 JwtUtil 测试类
- feat: 实现 Spring Security 配置 + JWT 过滤器（RBAC 权限控制）

## 2026-02-20

- style: 移除未使用的 import

## 2026-02-21

- build: 更新 Nacos 日志路径配置

## 2026-05-21

- docs: 移动 agent 文档到根目录，重命名 auth plan 文档
- build: 添加 Nacos + PostgreSQL 17 Docker 镜像
- feat: 添加 PostgreSQL 业务表 SQL
- feat: 添加表单 validation 依赖和校验注解
- feat: 接入 PostgreSQL 17 数据源，修复 javax → jakarta 迁移
- feat: Product 模块新增 8 个空 Controller 骨架
- feat: 实现 /category/list/tree 三级分类树形接口（非递归 O(n) 实现）
- refactor: 移除 @Service 冗余命名，更新 AGENTS.md
- build: 移除 IDE 配置文件，加入 .gitignore
- refactor: 移除废弃的 ProductController / Product / ProductControllerTest
- docs: Controller 添加 TODO 方法注释 + ServiceUris 常量
- refactor: ServiceUris 重构：新增 ProductCategory 内部类、注释统一改为中文
- docs: AGENTS.md 新增 Controller 编码规范（七条铁律）
- feat: 实现产品分类分页接口
- refactor: StringUtils 迁移至 Hutool StrUtil
- feat: 通用分页支持：BaseQueryDTO、PageUtils、MyBatis-Plus 分页插件配置
- refactor: PageUtils.selectPage 简化，整合分页执行 + VO 转换
- refactor: PRODUCT_SERVICE → PRODUCT_CATEGORY_SERVICE URI 重命名
- feat: BaseQueryDTO 新增 createTimeBegin / createTimeEnd 时间范围查询

## 2026-05-22

- feat: Product 模块 API 日志推送 Kafka（docker-compose + ApiLogInterceptor）
- feat: 日志增加请求体入参记录（ContentCachingFilter + body 字段）
- feat: CategoryController 新增子分类查询、批量删除、修改接口
- docs: 在 AGENTS.md 中添加 Git 提交信息格式规范
- docs: 创建 DEV_TIMELINE.md 作为开发时间线记录文件

- feat: 搭建 admin 前端工程骨架
- feat: HTTP 封装、Pinia Store、动态路由守卫（业内标准 getUserInfo 恢复菜单）
- docs: 创建 frontend/README.md、TODO.md 记录架构决策（Vue3.5 + Vite7 + ElementPlus2.12 + Pinia3 + auto-import + oxlint）
- feat: Layout 布局（CommonAside/Header/Menu/Tags）+ 登录页（对齐 template-vue3 风格，用户名+短信双模式）
- feat: 集成 unplugin-icons，本地 SVG 图标自动注册，3 个菜单图标
- docs: 明确 DEV_TIMELINE 须与代码同 commit，不分离提交
- refactor: 移除 guard.js /dev 开发快捷入口，统一走登录页
- docs: DEV_TIMELINE 新条目必须追加在末尾，禁止插入中间
- docs: 补充 frontend README 架构描述和示意图
- fix(admin): 修复 CommonTags 关闭逻辑（index 塌缩 + 菜单同步激活）
- fix(admin): 注册父级菜单路由，修复面包屑显示英文路径问题
- feat(admin): sidebar 菜单添加首页置顶项（HomeFilled 图标）
- feat: admin 前端骨架搭建完毕，标记 v0.1.0

- feat(admin): 搬运 template-vue3 菜单管理（含 commonTable + IconSelect + dateUtils 公共底座）

- refactor(admin): 图标系统切换 UnoCSS + presetIcons（替换 unplugin-icons，对齐 template-vue3）

## 2026-05-23

- style: 重构登录页左侧海报区排版与配色（Poppins 字体、深蓝配色体系、watermark 效果）
- feat(product): 从 wheatmall-2022 搬迁 Product 模块（15 Service+15 Impl+15 Mapper+10 Controller+22 VO+8 QueryDTO）
- refactor(product): 全部 Controller 路由改用 ServiceUris 常量，update/delete 走 @PathVariable {id}
- refactor(product): Controller 查询入参统一用专属 XxxQueryDTO extends BaseQueryDTO（8 个）
- refactor(product): Service 接口不暴露 Entity（saveSkuInfo 改 VO，VO 不引用 Entity）
- fix(product): 修复 18 个 ServiceImpl 孤儿 @Autowired（注解在方法上导致启动失败）
- refactor(product): Feign 替换为 WebClient 占位 Service（Coupon/Ware/Search 三个 RemoteService）
- docs: 新增规则——方法超过 40 行必须加 Javadoc + 行内注释
- docs: saveSupInfo 补 Javadoc + 8 处步骤注释

- test: 添加 CategoryController pytest + requests 接口测试 (16 个用例，覆盖 5 个接口)
- refactor: 12 个 ServiceImpl 的 save/updateById/removeById 方法从 void 改为返回 Integer（pass-through mapper 调用）
- refactor: vo 包 7 个类统一更名为 VO 后缀（BrandVo→BrandVO 等），IDEA 重构自动更新所有引用
- refactor: 16 个 ServiceImpl 添加类级别 @Transactional
- docs: 注释规则阈值从 40 行收紧为 15 行，AttrServiceImpl 全部方法补注释
- perf: saveBatch 全部改用 BaseMapper.insert(Collection) 批量插入（MyBatis-Plus 3.5.7+ 内置）
- refactor: Spring BeanUtils 统一替换为 Hutool BeanUtil
- refactor: 10 个 Service 的 save/saveBatch/updateById 参数从 Entity 改为 VO
- refactor: 12 个 removeByIds 和 9 个 getById 改为返回 Integer/VO，Service 层零 Entity 暴露
- refactor: ServiceUris 重构为 ProductServiceUris，BASE_URL 收入各 inner class，子路径绝对化
- refactor: 10 个 Controller 移除类级 @RequestMapping（子路径已绝对化）
- refactor: 提取 OrderServiceUris，Order URI 独立文件
- test: 添加 BrandController pytest 接口测试（13 个用例）

- refactor(product): 10 个 void Service 方法统一改为返回 Integer（delete/update/save 全链路透传影响行数）
- refactor(product): SpuInfoService.saveBaseSpuInfo 参数 Entity→VO，消除 Service 接口最后一处 Entity 暴露
- refactor(product): 2 个 Mapper 自定义方法 void→Integer（updateCategory/updaSpuStatus）

- feat(order): 从 wheatmall-2022 搬迁 Order 模块（9 Entity + 9 Mapper + 9 Service + 9 Impl + 9 VO + 9 QueryDTO + 9 Controller）
- refactor(order): 统一 2026 规范——Controller 路由常量/极薄转发/VO 入参出参，Service void→Integer，ServiceImpl @Transactional+BeanUtil 转换
- refactor(common): OrderServiceUris 补充 9 个内部类完整 URI 常量
- test: 添加 OrderController pytest 接口验证
- build: Order 模块添加 application.yml（PostgreSQL + Nacos + Kafka）
- feat(ware): 从 wheatmall-2022 搬迁 Ware 模块（6 Entity + 6 Mapper + 6 Service + 6 Impl + 6 VO + 3 business VO + 6 QueryDTO + 6 Controller）
- refactor(ware): 统一 2026 规范——Controller 路由常量/VO 入参，Service void→Integer，Feign→预留 RemoteService
- refactor(common): 新增 WareServiceUris 常量类 + SkuHasStockVO
- feat(search): 从 wheatmall-2022 搬迁 Search 模块（ES 索引 + 搜索服务）
- feat(thirdparty): 搬迁 Thirdparty 模块（OSS 签名 + 短信发送），手动创建 OssConfig 替代废弃的 spring-cloud-starter-alicloud-oss
- feat(thirdparty): 新增 OssConfig 配置类，手动装配 OSS Bean（Spring Cloud Alibaba 2025.x 已移除 alicloud-oss-starter）
- refactor(auth): wheatmall-auth-admin 重命名为 wheatmall-auth（目录/pom/包名/yml 统一更名，端口 8092→8096 避免与 order 冲突）
- feat(coupon): 从 wheatmall-2022 搬迁 Coupon 模块（16 Entity + 16 Mapper + 16 VO + 16 QueryDTO + 16 Service + 16 Controller + 16 XML）
- feat(common): 新增 CouponServiceUris 常量类 + Query/Constant 工具类 + SkuReductionTo/MemberPrice TO 类
- feat(member): 从 wheatmall-2022 搬迁 Member 模块（10 Entity + 10 Mapper + 13 VO + 10 QueryDTO + 10 Service + 10 Controller）
- feat(member): 保留会员注册/密码登录/Gitee OAuth 登录逻辑，Feign→CouponRemoteService 占位
- refactor(common): 新增 MemberServiceUris + UserLoginVO + UserRegisterVO + HttpUtils(RestTemplate)
- feat(gateway): 搬迁 Gateway 模块（Spring Cloud Gateway + Nacos 发现 + CORS + 8 条路由）
- feat(cart): 从 gulimall-dev 迁移 Cart 模块（Redis 购物车，7 个 REST API 端点）
- feat(common): 新增 CartConstant/CartVO/CartItemVO/SkuInfoVO/UserInfoTO/AuthConstant

## 2026-05-24

- refactor: SQL文件重命名 wheatmall→youhaowu，测试目录重命名 intergre-py-test→test
- refactor: 移除冗余 @MapperScan，Mapper 已全部使用 @Mapper 注解
- feat: 新建 youhaowu-pc-vue 电商 To C 前端骨架（Vue 3 + Vite + Element Plus + Pinia）

- feat(pc): 搭建首页骨架 12 组件（尚品汇风格）—— 分类hover导航 / Banner轮播 / 楼层 / 推荐 / 品牌 / 搜索

- fix(pc): 首页三栏等高 + 全局 border-box + 间距修复

- feat(pc): 加入 mock 数据 + 统一 API 层（DEV 自动走 mock）

- feat(admin): 商品分类管理页面（el-tree + 拖拽排序 + CRUD + 三级限制）

- feat(admin/product): 完善分类管理——新增 save/batchSort 后端接口 + 前端完整对接
- refactor(admin): 清理 WheatMall 命名残留 → 有好物
- style(admin): 分类管理操作栏风格与菜单管理统一
- style(admin): 分类管理树节点操作按钮加图标、调整间距对齐菜单管理风格
- feat(admin): 品牌管理 CRUD —— BrandVO 补全 + brandApi + brand.vue（QATable）
- refactor: 清理 31 个实体类中未使用的 import java.util.Date
- refactor: 批量替换 52 处 deleteBatchIds → deleteByIds（MyBatis-Plus deprecated）
- refactor: 清理 6 处未使用的 import BatchResult
- chore: VSCode 配置——关闭 null 分析、raw type 警告、开启保存时自动 organize imports
- refactor: SeckillServiceImpl 过时 StringUtils.isEmpty 替换为 Hutool StrUtil
- chore: 父 POM 加 -Xlint:-options 消除 javac 注解处理提示
- refactor: 全项目 StringUtils.isEmpty → Hutool StrUtil.isEmpty（消除过时 API 警告）
- refactor: RestHighLevelClient → ElasticsearchClient（ES 8.x 新客户端）
- refactor: selectBatchIds → selectByIds（MyBatis-Plus 过时 API）
- refactor: JwtUtil SignatureException 迁移到 jjwt 0.12 security 包
- fix: AttrRespVO 补充 @EqualsAndHashCode(callSuper=false) 消除 Lombok 警告

## 2026-05-26

- fix(build): 9 个子模块补充 spring-boot-maven-plugin，修复 no main manifest attribute 导致 JAR 无法启动
- fix: youhaowu-common 添加 spring-boot-starter-actuator，修复 /actuator/health 404
- refactor: gateway 依赖升级 spring-cloud-starter-gateway-server-webflux
- build: 新增 youhaowu-product Dockerfile
- build: Dockerfile 加 HEALTHCHECK 健康检查
- build: gateway 新增 Dockerfile + actuator 健康检查

## 2026-05-30

- ci: 新增 Jenkins Pipeline（参数化多模块部署）
- docs: README 加 Jenkins 部署文档 + AGENTS.md 加自动部署规则
- feat: 服务器环境检查脚本 env-install.py（Java/Docker 检查 + 自动创建网络 + PostgreSQL 部署）
- refactor: env-install.py 去掉 POSTGRES_DB，PG 容器名改为 postgres-17

## 2026-05-31

- feat: Nacos PostgreSQL 插件打包脚本 build-nacos.py（Maven Central 下载 + zipfile 注入 + Docker 镜像构建）
- feat: build-nacos.py 加 SQL 下载 + Nacos 数据库初始化步骤
- feat: build-nacos.py 配置集中化 + 加 Nacos 8080 端口 + 步骤 8 自启动容器
- feat: product 模块 Nacos 地址改为 129 + 加鉴权账号
- refactor: Nacos 容器启动迁移到 env-install.py docker-compose，去除 build-nacos.py 自启动
- refactor: SQL 下载 + Nacos 库初始化迁移到 env-install.py，用 POSTGRES_DB 自动建库
- fix: nacos-pg.sql 直接下载到 postgres-init/，修复复制遗漏 Bug
- refactor: env-install.py + build-nacos.py 移至 youhaowu-backend/scripts/
- feat: install_es_ik 加自定义词典（IKAnalyzer.cfg.xml + custom.dic），修复原版 ext_dict 为空导致不生效
- feat: 访问方式加 ES IK 分词说明 + 词典路径
- feat: env-install.py 加 Nacos 镜像存在性检查，缺失时提示运行 build-nacos-pg-image.py
- feat: env-install.py 加入 Nginx（前端静态 + 反代 Gateway），自动生成 nginx.conf
- fix: nginx.conf 去掉 mime.types include（conf 挂载覆盖了系统文件）
- feat: env-install.py 加入 Jenkins 容器（Maven/docker 组检查 + compose 集成）
- feat: env-install.py 加 -k 参数跳过已有容器 + 容器冲突检测
- fix: env-install.py -k 改为逐服务启动，Skip 模式不阻止其他容器启动
- feat: SQL 文件加 CREATE DATABASE IF NOT EXISTS，支持 PG 自动初始化建库
- feat: java-env-prepare.py SDKMAN + Java 21 + Maven + settings.xml 一键安装
- fix: Nacos 关闭 auth，解决 Gateway 注册 401- style(env): kafka 容器名简化 kafka / kafka-ui- refactor: kafka topic wheatmall.api.log → youhaowu.api.log- fix(env): kafka 权限修复（user: root + chmod 777）- fix(env): 撤掉 kafka user: root，仅保留 chmod 777- refactor: 全部 wheatmall 标识改为 youhaowu