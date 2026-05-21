# Nacos 3.1.1 + PostgreSQL 17 部署

Nacos 3.x 官方仅支持 MySQL，通过魔改 `nacos-server.jar` 注入 PG 驱动和映射插件，实现 PostgreSQL 支持。

## 架构

| 容器 | 镜像 | 端口 | 用途 |
|------|------|------|------|
| `nacos-postgres` | `postgres:17-alpine` | 5432 | Nacos 数据库 |
| `nacos-standalone` | `nacos/nacos-server:v3.1.1` | 8848(API), 8080(控制台) | Nacos 服务端 |

## 魔改原理

Nacos 3.x 的 PG 映射插件通过 Java SPI (`ServiceLoader`) 发现，**必须放在 `nacos-server.jar` 的 `BOOT-INF/lib/` 内**，外部挂载 `plugins/` 目录无效。

在宿主机将两个 JAR 塞入 `nacos-server.jar`：

```bash
mkdir -p BOOT-INF/lib
cp plugins/nacos-postgresql-datasource-plugin-ext-3.1.1.jar BOOT-INF/lib/
cp plugins/postgresql-42.7.4.jar BOOT-INF/lib/
zip -q nacos-server.jar BOOT-INF/lib/nacos-postgresql-datasource-plugin-ext-3.1.1.jar
zip -q nacos-server.jar BOOT-INF/lib/postgresql-42.7.4.jar
rm -rf BOOT-INF
```

其中：
- `nacos-postgresql-datasource-plugin-ext-3.1.1.jar` — 从 [nacos-group/nacos-plugin](https://github.com/nacos-group/nacos-plugin) `3.1.x.2` 标签编译的 PG 映射插件
- `postgresql-42.7.4.jar` — PG JDBC 驱动

## 启动

```bash
docker compose up -d
```

首次启动 PG 容器会自动执行 `postgres-init/nacos-pg.sql` 初始化 Nacos 数据库表。

## 访问

| 服务 | 地址 |
|------|------|
| Nacos API | http://localhost:8848/nacos |
| Nacos 控制台 | http://localhost:8080/ |
| 默认账号 | `nacos` / `nacos` |

## 关键配置

### `docker-compose.yml`

- `./nacos-server.jar:/home/nacos/target/nacos-server.jar` — 魔改 JAR 覆盖容器原版
- `NACOS_AUTH_TOKEN` / `NACOS_AUTH_IDENTITY_KEY/VALUE` — Nacos 3.1.1 强制要求

### `conf/application.properties`

```properties
spring.sql.init.platform=postgresql
db.pool.config.driverClassName=org.postgresql.Driver
db.url.0=jdbc:postgresql://172.20.0.10:5432/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user.0=faris
db.password.0=123456
```

`db.pool.config.driverClassName` 覆盖了 `ExternalDataSourceProperties.java` 中硬编码的 `com.mysql.cj.jdbc.Driver`。

## 重新构建 JAR

如果 Nacos 或 PG 插件版本更新，需重新编译并注入：

```bash
# 编译 PG 插件
git clone --branch 3.1.x https://github.com/nacos-group/nacos-plugin.git
cd nacos-plugin
mvn clean package -pl nacos-datasource-plugin-ext/nacos-postgresql-datasource-plugin-ext -am -DskipTests

# 从官方镜像提取 nacos-server.jar
docker create --name tmp nacos/nacos-server:v3.1.1
docker cp tmp:/home/nacos/target/nacos-server.jar .
docker rm tmp

# 注入插件和驱动
mkdir -p BOOT-INF/lib
cp nacos-postgresql-datasource-plugin-ext-3.1.1.jar BOOT-INF/lib/
cp postgresql-42.7.4.jar BOOT-INF/lib/
zip -q nacos-server.jar BOOT-INF/lib/nacos-postgresql-datasource-plugin-ext-3.1.1.jar
zip -q nacos-server.jar BOOT-INF/lib/postgresql-42.7.4.jar
rm -rf BOOT-INF
```
