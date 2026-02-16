# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

## 项目结构

```
wheatmall-2026/
├── backend/
│   ├── pom.xml                    # 父工程POM
│   ├── wheatmall-common/          # 公共模块
│   │   └── pom.xml
│   ├── wheatmall-product/         # 商品服务模块 (端口: 8081)
│   │   ├── src/main/java/com/wheatmall/product/
│   │   │   ├── ProductApplication.java
│   │   │   ├── entity/Product.java           # 商品实体类
│   │   │   └── controller/ProductController.java  # 商品接口
│   │   └── src/main/resources/application.yml
│   └── wheatmall-order/           # 订单服务模块 (端口: 8080)
│       ├── src/main/java/com/wheatmall/order/
│       │   ├── OrderApplication.java
│       │   ├── config/WebClientConfig.java      # WebClient配置
│       │   ├── dto/ProductDTO.java              # 商品DTO
│       │   ├── service/OrderService.java        # 订单服务(WebClient调用)
│       │   └── controller/OrderController.java  # 订单接口
│       └── src/main/resources/application.yml
└── README.md
```

## 开发记录

### 2024-02-16: 创建WebClient跨模块调用Demo

#### 1. 项目分析
- 分析了项目结构，确认使用Spring Boot 3.4.2 + Spring Cloud 2025.0.1
- 识别出两个核心模块：wheatmall-order 和 wheatmall-product
- wheatmall-common已包含webflux依赖，支持WebClient

#### 2. Lombok依赖配置调整
**修改文件：**
- `backend/wheatmall-common/pom.xml`
  - 添加Lombok依赖，移除optional标记，确保依赖可传递
  
- `backend/wheatmall-product/pom.xml` 和 `backend/wheatmall-order/pom.xml`
  - 添加Lombok依赖，scope设置为provided，确保编译时注解处理器可用
  
**注意：** Lombok需要在每个使用它的模块中配置注解处理器，不能仅通过传递依赖获得

#### 3. Product模块开发
**创建文件：**
- `backend/wheatmall-product/src/main/java/com/wheatmall/product/entity/Product.java`
  - 商品实体类，包含id、name、price、stock、description等字段
  - 使用Lombok简化代码

- `backend/wheatmall-product/src/main/java/com/wheatmall/product/controller/ProductController.java`
  - 提供商品查询REST接口
  - GET /api/product/{id} - 根据ID查询商品
  - GET /api/product/list - 查询所有商品列表
  - GET /api/product/get/{id} - 供内部服务调用

- `backend/wheatmall-product/src/main/resources/application.yml`
  - 配置服务端口为8081

#### 5. Order模块开发
**创建文件：**
- `backend/wheatmall-order/src/main/java/com/wheatmall/order/config/WebClientConfig.java`
  - 配置WebClient Bean
  - 配置Product服务的WebClient，baseUrl指向http://localhost:8081
  - 设置超时时间为5秒

- `backend/wheatmall-order/src/main/java/com/wheatmall/order/dto/ProductDTO.java`
  - 数据传输对象，用于接收Product模块返回的数据
  - 字段与Product实体类保持一致

- `backend/wheatmall-order/src/main/java/com/wheatmall/order/service/OrderService.java`
  - 使用WebClient调用Product模块的接口
  - 提供同步调用方法：getProductById(), getProductList()
  - 提供异步调用方法：getProductByIdAsync()返回Mono对象

- `backend/wheatmall-order/src/main/java/com/wheatmall/order/controller/OrderController.java`
  - 对外暴露订单相关接口
  - GET /api/order/product/{productId} - 同步获取商品信息
  - GET /api/order/products - 同步获取商品列表
  - GET /api/order/product/async/{productId} - 异步获取商品信息
  - POST /api/order/create - 创建订单(调用Product验证库存)

- `backend/wheatmall-order/src/main/resources/application.yml`
  - 配置服务端口为8080

#### 6. URI统一管理优化
**创建文件：**
- `backend/wheatmall-common/src/main/java/com/wheatmall/common/constant/ServiceUris.java`
  - 统一管理所有服务的API路径常量
  - 按服务分类：Product、Order、User等
  - 支持路径参数占位符（如`{id}`）

**第一次优化 - 使用常量类：**
- `OrderService.java`：使用`ServiceUris.Product.GET_BY_ID_INTERNAL`替换硬编码URI
- `ProductController.java`：使用`ServiceUris.Product.SERVICE`作为@RequestMapping路径

**第二次优化 - 服务根路径提升到外部类：**
- 在ServiceUris类顶部统一定义所有服务的根路径：
  ```java
  public static final String PRODUCT_SERVICE = "/api/product";
  public static final String ORDER_SERVICE = "/api/order";
  public static final String USER_SERVICE = "/api/user";
  ```
- 内部类引用外部根路径常量，结构更清晰
- `ProductController.java`更新为使用`ServiceUris.PRODUCT_SERVICE`

**优势：**
- 避免魔法字符串，提高代码可维护性
- 一眼查看所有服务的根路径，便于管理
- 修改根路径只需改一处，内部类自动生效
- 编译期检查，IDE自动补全支持
- 符合阿里巴巴Java开发手册规范

#### 7. WebClient使用要点
- **同步调用**：使用`.block()`方法阻塞获取结果
- **异步调用**：返回`Mono<T>`或`Flux<T>`对象，支持响应式编程
- **错误处理**：可通过`.onErrorResume()`处理异常
- **超时配置**：在HttpClient中设置responseTimeout

## 启动和测试

### 启动服务

1. 启动Product服务：
```bash
cd backend/wheatmall-product
mvn spring-boot:run
```

2. 启动Order服务：
```bash
cd backend/wheatmall-order
mvn spring-boot:run
```

### 测试接口

1. 测试Product模块直接访问：
```bash
# 获取单个商品
curl http://localhost:8081/api/product/1

# 获取商品列表
curl http://localhost:8081/api/product/list
```

2. 测试Order模块通过WebClient调用Product：
```bash
# 同步获取商品详情
curl http://localhost:8080/api/order/product/1

# 同步获取商品列表
curl http://localhost:8080/api/order/products

# 异步获取商品
curl http://localhost:8080/api/order/product/async/1

# 创建订单(内部调用Product服务)
curl -X POST "http://localhost:8080/api/order/create?productId=1&quantity=2"
```

### 测试结果

| 接口 | 地址 | 结果 |
|------|------|------|
| Product直接访问 | `GET http://localhost:8081/api/product/1` | 成功 |
| Order调用Product(同步) | `GET http://localhost:8080/api/order/product/1` | 成功 |
| Order调用Product(列表) | `GET http://localhost:8080/api/order/products` | 成功 |
| Order调用Product(异步) | `GET http://localhost:8080/api/order/product/async/2` | 成功 |
| 创建订单(库存验证) | `POST http://localhost:8080/api/order/create?productId=1&quantity=2` | 成功 |

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **WebClient**: Spring WebFlux提供的响应式HTTP客户端
- **Lombok**: 代码简化工具

## 后续优化建议

1. **服务注册发现**：集成Nacos或Eureka，使用服务名而非IP:Port调用
2. **负载均衡**：使用Spring Cloud LoadBalancer实现客户端负载均衡
3. **熔断降级**：集成Resilience4j实现服务容错
4. **链路追踪**：集成Sleuth+Zipkin追踪请求链路
5. **统一异常处理**：添加全局异常处理器
6. **参数校验**：集成Spring Validation进行参数校验
