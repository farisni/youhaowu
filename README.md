# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **Lombok**: 代码简化工具

## 项目特点

### WebClient 跨模块调用

Order 模块通过 `WebClient` + `@LoadBalanced` 以服务名方式调用 Product 模块，Nacos 自动完成服务发现与负载均衡。

**调用链路：**

```
OrderController → OrderService → WebClient → [Nacos 服务发现] → ProductController
         ↑                                                              │
         └────────────────── R<ProductDTO> ←────────────────────────────┘
```

**关键代码：**

```java
// 1. 配置支持负载均衡的 WebClient（Order 模块）
@Bean
@LoadBalanced
public WebClient.Builder loadBalancedWebClientBuilder() {
    return WebClient.builder();
}

@Bean
public WebClient productWebClient(WebClient.Builder builder) {
    return builder.baseUrl("http://wheatmall-product").build();  // 服务名，非 IP
}

// 2. 同步调用
public ProductDTO getProductById(Long productId) {
    return productWebClient.get()
            .uri("/api/product/get/{id}", productId)
            .retrieve()
            .bodyToMono(ProductDTO.class)
            .block();
}

// 3. 异步调用
public Mono<ProductDTO> getProductByIdAsync(Long productId) {
    return productWebClient.get()
            .uri("/api/product/get/{id}", productId)
            .retrieve()
            .bodyToMono(ProductDTO.class);  // 非阻塞，返回 Mono
}
```

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



