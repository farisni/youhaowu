# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **WebClient**: Spring WebFlux提供的响应式HTTP客户端
- **Lombok**: 代码简化工具

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



