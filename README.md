# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **Lombok**: 代码简化工具

## 项目特点

- **微服务架构**：基于 Spring Cloud Alibaba，Nacos 服务注册发现，模块独立部署
- **跨模块调用**：WebClient + LoadBalancer 实现响应式服务间调用
- **统一响应**：泛型 R 类封装标准 API 响应格式（code / msg / data）
- **RBAC 认证**：Spring Security + JWT 无状态认证，角色权限控制
- **API 可观测**：Kafka 异步推送 API 日志，拦截器记录请求体入参
- **通用分页**：BaseQueryDTO + PageUtils 封装 MyBatis-Plus 分页，支持时间范围筛选
- **PostgreSQL 持久化**：PostgreSQL 17 数据源，javax → jakarta 平滑迁移

## 项目结构

```
wheatmall-2026/
├── backend/
│   ├── pom.xml                    # 父工程POM
│   ├── wheatmall-common/          # 公共模块
│   ├── wheatmall-product/         # 商品服务模块 (端口: 8091)
│   ├── wheatmall-order/           # 订单服务模块 (端口: 8090)
│   └── wheatmall-auth-admin/      # 认证授权模块 (端口: 8092)
├── frontend/                      # 前端项目
├── doc/                           # 文档与配置
└── README.md
```



