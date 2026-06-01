# 有好物

基于 Spring Boot 的微服务商城系统。

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 21
- **Nacos**: 服务注册发现
- **PostgreSQL 17**: 数据库
- **Kafka**: 消息队列
- **MyBatis-Plus**: ORM

## 项目结构

```
youhaowu/
├── backend/
│   ├── pom.xml                     # 父工程 POM
│   ├── youhaowu-common/           # 公共模块（R、PageUtils、ServiceUris 等）
│   ├── youhaowu-gateway/          # 网关服务（:8888）
│   ├── youhaowu-product/          # 商品服务（:8091）
│   ├── youhaowu-order/            # 订单服务（:8092）
│   ├── youhaowu-ware/             # 仓储服务（:8093）
│   ├── youhaowu-search/           # 搜索服务（:8094）
│   ├── youhaowu-thirdparty/       # 第三方服务（:8095）
│   ├── youhaowu-auth/             # 认证服务（:8096）
│   ├── youhaowu-coupon/           # 优惠券服务（:8097）
│   └── youhaowu-member/           # 会员服务（:8098）
│   ├── youhaowu-cart/              # 购物车服务（:8099）
├── frontend/                       # 前端项目
└── README.md
```

## 微服务调用关系

```
                        ┌─────────────────┐
                        │     Gateway      │
                        │      :8888       │
                        └───┬──┬──┬──┬───┬┘
                            │  │  │  │   │
        ┌─────┬─────┬───────┘  │  │  │   └──────┬──────┐
        ▼     ▼     ▼          │  │  │          ▼      ▼
   ┌──────────┐ ┌──────────┐   │  │  │   ┌──────────┐ ┌──────────┐
   │ product  │ │  order   │   │  │  │   │  search  │ │   auth   │
   │  :8091   │ │  :8092   │   │  │  │   │  :8094   │ │  :8096   │
   └──┬──┬──┬─┘ └──────────┘   │  │  │   └────┬─────┘ └──┬───┬───┘
      │  │  │                 │  │  │        │          │   │
      │  │  └──→ search ──────┼──┼──┼────────┘          │   │
      │  │    ⑤ 索引上架       │  │  │  ④ 查属性          │   │
      │  └────→ coupon ───────┼──┼──┼───────────────────┼───┼──┐
      │    ③ 保存积分/满减      │  │  │                    │   │  │
      └───────→ ware          │  │  │                    │   │  │
        ③ 查库存               │  │  │                    │   │  │
                               │  │  │                    │   │  │
   ┌──────────┐ ┌──────────┐   │  │  │                    │   │  │
   │  coupon  │ │  member  │   │  │  │                    │   │  │
   │  :8097   │ │  :8098   │   │  │  │                    │   │  │
   └──────────┘ └────┬─────┘   │  │  │                    │   │  │
        ▲            │         │  │  │                    │   │  │
        │            └──→ coupon ←┘  │                    │   │  │
        │             ⑥ 会员券       │                    │   │  │
        │                           │                    │   │  │
        └──────── product ──────────┘                    │   │  │
          ③ 保存积分/满减                                 │   │  │
                                                        │   │  │
   ┌──────────┐                     ┌──────────┐        │   │  │
   │   ware   │                     │thirdparty│ ◄──────┘   │  │
   │  :8093   │                     │  :8095   │  ⑨ 发短信  │  │
   └────┬─────┘                     └──────────┘            │  │
        │                                                   │  │
        └──────────→ product ───────────────────────────────┘  │
             ⑦ 查属性                                         │
                                                               │
        order ──→ ware ────────────────────────────────────────┘
          ② 锁库存

        auth ──⑧ 注册/登录/Gitee OAuth─→ member
```

| # | 调用 | 业务 | 状态 |
|---|------|------|------|
| ① | order → product | 创建订单查商品信息 | 缺失 |
| ② | order → ware | 锁定/解锁库存 | 缺失 |
| ③ | product → coupon / ware | 上架同步积分满减 + 查库存 | 占位 |
| ④ | search → product | 搜索结果查属性/品牌 | 空壳 |
| ⑤ | product → search | 上架批量索引 SKU 到 ES | 占位 |
| ⑥ | member → coupon | 会员优惠券列表 | 占位 |
| ⑦ | ware → product | 采购单查商品属性 | 缺失 |
| ⑧ | auth → member | 注册 / 登录 / Gitee OAuth | 缺失 |
| ⑨ | auth → thirdparty | 短信验证码 | 缺失 |

## 快速开始

```bash
# 启动 Nacos + PostgreSQL + Kafka
cd backend && docker-compose up -d

# 编译全部模块
mvn compile -q

# 运行单个模块
mvn spring-boot:run -pl youhaowu-product
```


## 基础设施脚本

### env-install.py
服务器环境检查 + 一键启动 PostgreSQL + Nacos。

```
# 检查环境 + 自动初始化 + 启动
python3 env-install.py
```
流程：检查 Java/Docker → 创建 Docker 网络 → 生成 docker-compose.yml → 下载 nacos-pg.sql → 启动容器

### build-nacos-pg-image.py
构建带 PostgreSQL 插件的 Nacos Docker 镜像（产物 `/tmp/nacos/`）。

```
# 构建镜像
python3 build-nacos-pg-image.py
```
流程：下载 PG 插件 + JDBC 驱动 → 提取官方 Nacos JAR → 注入 → docker build

> 两个脚本都在 `youhaowu-backend/scripts/` 下，部署到服务器的 `~/Code/`。

## 测试

```bash
# 集成测试（pytest + requests）
cd backend/intergre-py-test
python3 -m pytest test_category.py -v
python3 -m pytest test_order.py -v
python3 -m pytest test_coupon.py -v
```

---

## Jenkins 部署

### 环境

- Jenkins 2.541.3（Docker 部署在 192.168.8.112）
- Pipeline 支持参数化多模块选择
- Docker Healthcheck 健康检查

### 快速触发部署

```bash
# 从本地触发（安装 jenkins-cli）
curl -O http://192.168.8.112:8070/jnlpJars/jenkins-cli.jar

# 部署商品服务
java -jar jenkins-cli.jar -s http://192.168.8.112:8070 -auth <user>:<token> build youhaowu -p MODULE=youhaowu-product

# 部署网关
java -jar jenkins-cli.jar -s http://192.168.8.112:8070 -auth <user>:<token> build youhaowu -p MODULE=youhaowu-gateway
```

### 推荐 alias

```bash
# 加到 ~/.zshrc
alias deploy-product='java -jar ~/jenkins-cli.jar -s http://192.168.8.112:8070 -auth faris:$JENKINS_TOKEN build youhaowu -p MODULE=youhaowu-product'
alias deploy-gateway='java -jar ~/jenkins-cli.jar -s http://192.168.8.112:8070 -auth faris:$JENKINS_TOKEN build youhaowu -p MODULE=youhaowu-gateway'
```

### Pipeline 流程

1. **检出代码** — 拉取 Gitee main 分支
2. **Maven 打包** — 编译 + 跳过测试
3. **Docker 构建** — 多阶段镜像构建（含 HEALTHCHECK）
4. **部署** — stop/rm/run 滚动更新
5. **健康检查** — 轮询 `docker inspect Health.Status`，最多 60s
6. **查看状态** — 输出容器状态 + 最新日志

### 加新模块

1. 在模块目录下加 `Dockerfile`（含 HEALTHCHECK）
2. 在 Jenkins Pipeline 的 `parameters.choices` 里加模块名
