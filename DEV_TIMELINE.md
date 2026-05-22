# 开发时间线 (Development Timeline)

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
- feat: Layout 布局（CommonAside/Header/Menu/Tags）+ 登录页（对齐 template-vue3 风格，用户名+短信双模式）
- feat: 集成 unplugin-icons，本地 SVG 图标自动注册，3 个菜单图标
- docs: 明确 DEV_TIMELINE 须与代码同 commit，不分离提交
- refactor: 移除 guard.js /dev 开发快捷入口，统一走登录页
- docs: 创建 frontend/README.md、TODO.md 记录架构决策（Vue3.5 + Vite7 + ElementPlus2.12 + Pinia3 + auto-import + oxlint）
- docs: DEV_TIMELINE 新条目必须追加在末尾，禁止插入中间
