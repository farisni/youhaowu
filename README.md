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
├── frontend/                       # 前端项目
└── README.md
```

## 微服务调用关系

```
                        ┌─────────────────┐
                        │     Gateway      │
                        │     :8888        │
                        └───┬──┬──┬──┬───┘
                            │  │  │  │
        ┌─────┬─────┬───────┘  │  │  └───────┬──────┬──────┐
        ▼     ▼     ▼          │  │          ▼      ▼      ▼
   ┌──────────┐ ┌──────────┐ ┌─┴──────────┐ ┌──────────┐ ┌──────────┐
   │ product  │ │  order   │ │    ware    │ │  search  │ │  coupon  │
   │  :8091   │ │  :8092   │ │   :8093    │ │  :8094   │ │  :8097   │
   └──┬──┬──┬─┘ └──────────┘ └────────────┘ └──────────┘ └────┬─────┘
      │  │  │                                                  │
      │  │  └─────────> search ────────────────┐               │
      │  └───────────> coupon ─────────────────┼───────────────┘
      └──────────────> ware                   │
         product 上架时:                       │
         • 调 coupon 保存积分+满减             │
         • 调 search 批量索引到 ES             │
         • 调 ware 查询库存                    │
                                               │
   ┌──────────┐ ┌──────────┐                  │
   │  member  │ │   auth   │                  │
   │  :8098   │ │  :8096   │                  │
   └────┬─────┘ └────┬──┬──┘                  │
        │            │  │                     │
        └──→ coupon  │  └──→ thirdparty       │
          查询优惠券  │      发送短信          │
                     │                        │
                     └──────────→ member ─────┘
                      注册/登录/Gitee OAuth
                      
   ┌──────────┐       ┌──────────┐
   │thirdparty│       │  search  │────────> product
   │  :8095   │       │  :8094   │    查询属性/品牌详情
   └──────────┘       └──────────┘
```

### 跨服务调用明细

| 调用方 | 被调用方 | 接口 | 用途 | 状态 |
|--------|---------|------|------|------|
| product | coupon | `saveSpuBounds` | SPU 上架同步保存积分 | 占位 |
| product | coupon | `saveSkuReduction` | SKU 上架同步保存满减+会员价 | 占位 |
| product | ware | `getSkuHasStock` | 查询 SKU 库存 | 占位 |
| product | search | `productStatusUp` | 上架时批量索引 SKU 到 ES | 占位 |
| ware | product | `attrInfo` | 采购单查询商品属性 | 缺失 |
| member | coupon | `memberCoupons` | 查询会员优惠券 | 占位 |
| auth | member | `register / login / oauthLogin` | 认证服务调用会员注册登录 | 缺失 |
| auth | thirdparty | `sendCode` | 发送短信验证码 | 缺失 |
| search | product | `attrInfo` | 搜索页查询属性详情 | 空壳 |
| order | product | (查 SPU/SKU) | 订单创建时查询商品信息 | 缺失 |
| order | ware | (锁库存/解锁) | 订单创建/取消时操作库存 | 缺失 |

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
