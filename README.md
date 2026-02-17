# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

## 项目结构

```
wheatmall-2026/
├── backend/
│   ├── pom.xml                    # 父工程POM
│   ├── wheatmall-common/          # 公共模块
│   │   ├── pom.xml
│   │   ├── src/main/java/com/wheatmall/common/
│   │   │   ├── constant/ServiceUris.java       # 服务URI常量
│   │   │   ├── enums/BizCodeEnum.java          # 业务错误码枚举
│   │   │   └── utils/R.java                    # 通用响应结果类
│   │   └── src/test/java/com/wheatmall/common/
│   │       └── utils/RTest.java                # R类单元测试
│   ├── wheatmall-product/         # 商品服务模块 (端口: 8091)
│   │   ├── src/main/java/com/wheatmall/product/
│   │   │   ├── ProductApplication.java
│   │   │   ├── entity/Product.java           # 商品实体类
│   │   │   └── controller/ProductController.java  # 商品接口
│   │   ├── src/main/resources/application.yml    # Nacos配置
│   │   └── src/test/java/com/wheatmall/product/
│   │       └── controller/ProductControllerTest.java  # Controller单元测试
│   └── wheatmall-order/           # 订单服务模块 (端口: 8090)
│       ├── src/main/java/com/wheatmall/order/
│       │   ├── OrderApplication.java
│       │   ├── config/WebClientConfig.java      # WebClient+Nacos负载均衡配置
│       │   ├── dto/ProductDTO.java              # 商品DTO
│       │   ├── service/OrderService.java        # 订单服务(WebClient+Nacos调用)
│       │   └── controller/OrderController.java  # 订单接口
│       └── src/main/resources/application.yml    # Nacos配置
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
  - 配置服务端口为8091

#### 5. Order模块开发
**创建文件：**
- `backend/wheatmall-order/src/main/java/com/wheatmall/order/config/WebClientConfig.java`
  - 配置WebClient Bean
  - 配置Product服务的WebClient，baseUrl指向http://localhost:8091
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
  - 配置服务端口为8090

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
curl http://localhost:8091/api/product/1

# 获取商品列表
curl http://localhost:8091/api/product/list
```

2. 测试Order模块通过WebClient调用Product：
```bash
# 同步获取商品详情
curl http://localhost:8090/api/order/product/1

# 同步获取商品列表
curl http://localhost:8090/api/order/products

# 异步获取商品
curl http://localhost:8090/api/order/product/async/1

# 创建订单(内部调用Product服务)
curl -X POST "http://localhost:8090/api/order/create?productId=1&quantity=2"
```

### 测试结果

| 接口 | 地址 | 结果 |
|------|------|------|
| Product直接访问 | `GET http://localhost:8091/api/product/1` | 成功 |
| Order调用Product(同步) | `GET http://localhost:8090/api/order/product/1` | 成功 |
| Order调用Product(列表) | `GET http://localhost:8090/api/order/products` | 成功 |
| Order调用Product(异步) | `GET http://localhost:8090/api/order/product/async/2` | 成功 |
| 创建订单(库存验证) | `POST http://localhost:8090/api/order/create?productId=1&quantity=2` | 成功 |

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **WebClient**: Spring WebFlux提供的响应式HTTP客户端
- **Lombok**: 代码简化工具

## Java对象命名规范

| 缩写 | 全称 | 中文含义 | 使用场景 | 说明 |
|------|------|----------|----------|------|
| DTO | Data Transfer Object | 数据传输对象 | Service ↔ Controller | 层间数据传输 |
| VO | View Object | 视图对象 | Controller ↔ 前端 | 返回给前端展示 |
| DO/PO | Domain/Persistent Object | 领域/持久化对象 | DAO ↔ Database | 数据库表映射 |
| BO | Business Object | 业务对象 | Service 层 | 封装业务逻辑 |
| Query | Query Object | 查询对象 | Controller → Service | 封装查询参数 |
| POJO | Plain Old Java Object | 简单Java对象 | 任意 | 普通Java对象 |

**使用原则：**
- Controller 返回前端统一使用 VO
- Service 层接收参数使用 DTO 或 Query
- 与数据库交互使用 DO/PO
- 复杂业务逻辑封装在 BO 中

### 2024-02-17: 集成Nacos 3.1.1服务注册发现

#### 1. 添加Nacos依赖
**修改文件：**
- `backend/wheatmall-common/pom.xml`
  - 添加 `spring-cloud-starter-alibaba-nacos-discovery` 依赖
  - 添加 `spring-cloud-starter-loadbalancer` 依赖（支持客户端负载均衡）

**版本说明：**
- Nacos Server版本：3.1.1
- Spring Cloud Alibaba版本：2025.0.0.0（已在父POM中管理）

#### 2. 启用服务发现
**修改文件：**
- `backend/wheatmall-product/src/main/java/com/wheatmall/product/ProductApplication.java`
  - 添加 `@EnableDiscoveryClient` 注解启用Nacos服务注册
  
- `backend/wheatmall-order/src/main/java/com/wheatmall/OrderApplication.java`
  - 添加 `@EnableDiscoveryClient` 注解启用Nacos服务注册

#### 3. 配置Nacos注册中心
**修改文件：**
- `backend/wheatmall-product/src/main/resources/application.yml`
  - 添加Nacos配置：server-addr指向127.0.0.1:8848
  - namespace使用public，group使用DEFAULT_GROUP
  
- `backend/wheatmall-order/src/main/resources/application.yml`
  - 添加相同的Nacos配置

#### 3. 修改WebClient配置
**修改文件：**
- `backend/wheatmall-order/src/main/java/com/wheatmall/order/config/WebClientConfig.java`
  - WebClient.Builder添加 `@LoadBalanced` 注解启用负载均衡
  - 修改baseUrl从 `http://localhost:8091` 改为 `http://wheatmall-product`
  - 删除手动配置的超时设置（可通过其他方式配置）

**服务调用方式变化：**
```java
// 之前：直接IP调用
.baseUrl("http://localhost:8091")

// 之后：通过Nacos服务名调用
.baseUrl("http://wheatmall-product")
```

#### 4. 启动前准备
**需要启动Nacos Server：**
```bash
# 下载并启动Nacos 3.1.1
sh startup.sh -m standalone
```

**Nacos控制台访问：** http://127.0.0.1:8080/nacos (默认账号密码：nacos/nacos)
**客户端注册地址：** 127.0.0.1:8848

## 启动和测试

### 前置条件
确保Nacos Server已启动：
- 控制台：http://127.0.0.1:8080/nacos
- 客户端注册地址：127.0.0.1:8848

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

3. 查看Nacos控制台：
- 访问 http://127.0.0.1:8080/nacos
- 在服务列表中应能看到 `wheatmall-product` 和 `wheatmall-order`

### 测试接口

1. 测试Product模块直接访问：
```bash
# 获取单个商品
curl http://localhost:8091/api/product/1

# 获取商品列表
curl http://localhost:8091/api/product/list
```

2. 测试Order模块通过Nacos调用Product：
```bash
# 同步获取商品详情（通过Nacos服务名调用）
curl http://localhost:8090/api/order/product/1

# 同步获取商品列表
curl http://localhost:8090/api/order/products

# 异步获取商品
curl http://localhost:8090/api/order/product/async/1

# 创建订单(内部通过Nacos调用Product服务)
curl -X POST "http://localhost:8090/api/order/create?productId=1&quantity=2"
```

### 测试结果

| 接口 | 地址 | 结果 |
|------|------|------|
| Product直接访问 | `GET http://localhost:8091/api/product/1` | 成功 |
| Order调用Product(通过Nacos) | `GET http://localhost:8090/api/order/product/1` | 成功 |
| Order调用Product(列表) | `GET http://localhost:8090/api/order/products` | 成功 |
| Order调用Product(异步) | `GET http://localhost:8090/api/order/product/async/2` | 成功 |
| 创建订单(Nacos调用) | `POST http://localhost:8090/api/order/create?productId=1&quantity=2` | 成功 |

### 2024-02-17: 整合Mockito单元测试框架

#### 1. 添加Mockito依赖
**修改文件：**
- `backend/pom.xml`
  - 添加 `mockito.version` 属性
  - 在 `dependencyManagement` 中添加 `mockito-core` 和 `mockito-junit-jupiter`

- `backend/wheatmall-common/pom.xml`
  - 添加 `spring-boot-starter-test`、`mockito-core`、`mockito-junit-jupiter` 测试依赖

- `backend/wheatmall-product/pom.xml`
  - 添加 `spring-boot-starter-test`、`mockito-core`、`mockito-junit-jupiter` 测试依赖

**依赖说明：**
- `mockito-core` - Mockito核心库
- `mockito-junit-jupiter` - 与JUnit 5集成的扩展
- `spring-boot-starter-test` - Spring Boot测试启动器（已包含JUnit 5和Mockito）

#### 2. 创建测试类
**创建文件：**
- `backend/wheatmall-common/src/test/java/com/wheatmall/common/utils/RTest.java`
  - R类的单元测试，验证ok/error/setData/put等方法
  - 测试链式调用和fastjson反序列化

- `backend/wheatmall-product/src/test/java/com/wheatmall/product/controller/ProductControllerTest.java`
  - ProductController的单元测试
  - 使用 `@ExtendWith(MockitoExtension.class)` 扩展
  - 使用 `@InjectMocks` 注入被测对象

**Mockito常用注解：**
```java
@ExtendWith(MockitoExtension.class)  // 启用Mockito扩展
@InjectMocks                         // 注入被测对象
@Mock                                // 创建模拟对象
@Spy                                 // 创建部分模拟对象
@Captor                              // 捕获参数
```

#### 3. 运行测试
```bash
# 运行所有测试
mvnd test

# 运行指定模块测试
mvnd test -pl wheatmall-common

# 运行指定测试类
mvnd test -Dtest=RTest -pl wheatmall-common
```

### 2024-02-17: 添加通用响应结果类R

#### 1. 添加依赖
**修改文件：**
- `backend/wheatmall-common/pom.xml`
  - 添加 `fastjson` 依赖（JSON序列化）
  - 添加 `httpcore` 依赖（HttpStatus常量）

#### 2. 创建通用响应类
**创建文件：**
- `backend/wheatmall-common/src/main/java/com/wheatmall/common/utils/R.java`
  - 继承HashMap，提供统一的API响应格式
  - 包含code、msg、data标准字段
  - 支持链式调用（put、setData等）
  - 支持fastjson反序列化（getData方法）
  - 提供ok()和error()静态工厂方法

**使用示例：**
```java
// 成功响应
return R.ok().setData(product);

// 带消息的成功响应
return R.ok("操作成功");

// 错误响应
return R.error(BizCodeEnum.PRODUCT_NOT_FOUND);

// 自定义错误
return R.error(500, "系统错误");
```

#### 3. 创建业务错误码枚举
**创建文件：**
- `backend/wheatmall-common/src/main/java/com/wheatmall/common/enums/BizCodeEnum.java`
  - 定义常见业务错误码
  - 系统错误、参数错误、权限错误等
  - 商品相关错误（不存在、已下架等）
  - 订单相关错误（库存不足、创建失败等）

## 后续优化建议

1. ~~**服务注册发现**：集成Nacos或Eureka，使用服务名而非IP:Port调用~~ ✅ 已完成
2. ~~**负载均衡**：使用Spring Cloud LoadBalancer实现客户端负载均衡~~ ✅ 已完成（通过@LoadBalanced）
3. ~~**统一响应格式**：添加通用响应结果类R~~ ✅ 已完成
4. ~~**单元测试**：整合Mockito测试框架~~ ✅ 已完成
5. **配置中心**：使用Nacos Config统一管理配置
6. **JWT认证**：正在规划中 - wheatmall-auth-admin 模块

### 2026-02-17: 创建 wheatmall-auth-admin 认证模块（空白框架）

**执行步骤：**
1. [x] 创建模块目录结构
2. [x] 创建 pom.xml 配置文件
3. [x] 创建启动类 AuthAdminApplication
4. [x] 创建 README.md 模块说明
5. [x] 创建 doc/JWT_AUTH_DEVELOPMENT_PLAN.md 开发规划文档
6. [x] 更新父工程 pom.xml 添加新模块

**创建的文件：**
- `backend/wheatmall-auth-admin/pom.xml` - Maven配置（依赖：Spring Security、JWT、Redis、Nacos）
- `backend/wheatmall-auth-admin/src/main/java/com/wheatmall/authadmin/AuthAdminApplication.java` - 启动类
- `backend/wheatmall-auth-admin/README.md` - 模块说明文档
- `backend/wheatmall-auth-admin/doc/JWT_AUTH_DEVELOPMENT_PLAN.md` - JWT认证系统完整开发规划

**模块信息：**
- 服务名：wheatmall-auth-admin
- 端口：8092
- 功能定位：统一认证授权管理（JWT + RBAC）

**开发规划包含：**
- 8个Phase共25个执行步骤
- 完整的架构图和流程图
- 详细的代码示例和配置
- 数据库表结构设计（RBAC三表）
- 预计总工时：约5小时

### 2026-02-17: 添加AuthService和JwtUtil测试类

**执行步骤：**
1. [x] 创建DTO/VO对象(LoginRequest/RefreshTokenRequest/LoginResponse/UserInfoVO)
2. [x] 创建AuthService接口和简单实现
3. [x] 创建JwtUtilTest测试类(21个测试用例)
4. [x] 创建AuthServiceTest测试类(10个测试用例)
5. [x] AuthServiceTest.testLoginSuccess添加详细注释说明
6. [x] 运行测试验证(31个测试全部通过)

**创建的文件：**
- `backend/wheatmall-auth-admin/src/main/java/com/wheatmall/authadmin/dto/` - DTO对象
- `backend/wheatmall-auth-admin/src/main/java/com/wheatmall/authadmin/vo/` - VO对象  
- `backend/wheatmall-auth-admin/src/main/java/com/wheatmall/authadmin/service/` - AuthService
- `backend/wheatmall-auth-admin/src/test/java/com/wheatmall/authadmin/security/jwt/JwtUtilTest.java` - JwtUtil测试
- `backend/wheatmall-auth-admin/src/test/java/com/wheatmall/authadmin/service/AuthServiceTest.java` - AuthService测试

**测试覆盖：**
- JwtUtil: Token生成/解析/验证/类型判断/异常处理
- AuthService: 登录/登出/刷新Token/参数校验
- **测试结果：31个测试全部通过** ✓
