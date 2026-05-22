# 开发时间线 (Development Timeline)

## 2026-02-16

- 项目初始化，创建 wheatmall-2026 仓库
- 实现 Order 模块通过 WebClient 调用 Product 模块（跨模块调用 Demo）
- 使用常量类 ServiceUris 统一管理 URI 路径

## 2026-02-17

- 集成 Nacos 3.1.1 服务注册发现
- 添加通用响应结果类 R（泛型版本）
- ProductController 接口统一返回 R 对象
- 整合 Mockito + JUnit 5 单元测试框架
- 为 order 模块添加单元测试
- 更新 AI 操作规则文档（测试验证、Git 提交、文档记录、需求拆分规则）
- 添加 Java 对象命名规范
- 创建 wheatmall-auth-admin 认证模块框架
- 添加 JWT 工具类和假数据模型
- 添加 AuthService 和 JwtUtil 测试类
- 实现 Spring Security 配置 + JWT 过滤器（RBAC 权限控制）

## 2026-02-20

- 移除未使用的 import

## 2026-02-21

- 更新 Nacos 日志配置
- 合并远程 main 分支

## 2026-05-21

- 移动 agent 文档到根目录，重命名 auth plan 文档
- 添加 Nacos + PostgreSQL 17 镜像和业务表
- 添加表单 validation 依赖
- 接入 PostgreSQL 17 数据源，修复 javax → jakarta 迁移
- Product 模块新增 8 个空 Controller 骨架
- 实现 /category/list/tree 三级分类树形接口（非递归 O(n) 实现）
- 移除 @Service 冗余命名，更新 AGENTS.md
- 移除 IDE 配置文件，加入 .gitignore
- 移除废弃的 ProductController / Product / ProductControllerTest
- Controller 添加 TODO 方法注释 + ServiceUris 常量
- ServiceUris 重构：新增 ProductCategory 内部类、注释统一改为中文
- AGENTS.md 新增 Controller 编码规范（七条铁律）
- 实现产品分类分页接口
- StringUtils 迁移至 Hutool StrUtil
- 通用分页支持：BaseQueryDTO、PageUtils、MyBatis-Plus 分页插件配置
- PageUtils.selectPage 简化，整合分页执行 + VO 转换
- PRODUCT_SERVICE → PRODUCT_CATEGORY_SERVICE URI 重命名
- BaseQueryDTO 新增 createTimeBegin / createTimeEnd 时间范围查询

## 2026-05-22

- Product 模块 API 日志推送 Kafka（docker-compose + ApiLogInterceptor）
- 日志增加请求体入参记录（ContentCachingFilter + body 字段）
- CategoryController 新增子分类查询、批量删除、修改接口
- 在 AGENTS.md 中添加 Git 提交信息格式规范（`<type>(<scope>): <中文描述>`）
- 创建 DEV_TIMELINE.md 作为开发时间线记录文件
