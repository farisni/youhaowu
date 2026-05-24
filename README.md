# WheatMall

基于 Spring Boot 的微服务商城系统，从 wheatmall-2022 迁移而来。

## 技术栈

- **Spring Boot**: 3.4.2
- **Spring Cloud**: 2025.0.1
- **Spring Cloud Alibaba**: 2025.0.0.0
- **Java**: 17
- **Nacos**: 服务注册发现
- **PostgreSQL**: 数据库
- **Kafka**: 消息队列
- **MyBatis-Plus**: ORM

## 项目结构

```
wheatmall-2026/
├── backend/
│   ├── pom.xml                     # 父工程 POM
│   ├── wheatmall-common/           # 公共模块（R、PageUtils、ServiceUris 等）
│   ├── wheatmall-gateway/          # 网关服务（:8888）
│   ├── wheatmall-product/          # 商品服务（:8091）
│   ├── wheatmall-order/            # 订单服务（:8092）
│   ├── wheatmall-ware/             # 仓储服务（:8093）
│   ├── wheatmall-search/           # 搜索服务（:8094）
│   ├── wheatmall-thirdparty/       # 第三方服务（:8095）
│   ├── wheatmall-auth/             # 认证服务（:8096）
│   ├── wheatmall-coupon/           # 优惠券服务（:8097）
│   └── wheatmall-member/           # 会员服务（:8098）
│   ├── wheatmall-cart/              # 购物车服务（:8099）
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
mvn spring-boot:run -pl wheatmall-product
```

## 测试

```bash
# 集成测试（pytest + requests）
cd backend/intergre-py-test
python3 -m pytest test_category.py -v
python3 -m pytest test_order.py -v
python3 -m pytest test_coupon.py -v
```
