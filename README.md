# WheatMall 项目开发记录

本项目是一个基于Spring Boot的微服务商城系统，包含订单(order)和商品(product)两个模块。

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

| POJO | Plain Old Java Object | 简单Java对象 | 任意 | 普通Java对象 |

**使用原则：**
- Controller 返回前端统一使用 VO
- Service 层接收参数使用 DTO 或 Query
- 与数据库交互使用 DO/PO
- 复杂业务逻辑封装在 BO 中

### 2026-02-17: 集成Nacos 3.1.1服务注册发现

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

### 2026-02-17: 整合Mockito单元测试框架

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

### 2026-02-17: 添加通用响应结果类R

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

