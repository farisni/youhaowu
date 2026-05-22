# WheatMall Admin 前端

基于 Vue 3.5 + Vite 7 + Element Plus 2.12 的后台管理系统。

## 架构思路

RBAC + Dynamic Route Injection 实现中后台

![ChatGPT Image 2026年5月22日 21_33_29.png|250](https://faris-note-picture.oss-cn-guangzhou.aliyuncs.com/ChatGPT%20Image%202026%E5%B9%B45%E6%9C%8822%E6%97%A5%2021_33_29.png)
结合两个参考项目的优点：

| 来自 | 取什么 | 不取什么 |
|---|---|---|
| **youhaowu-app** | Composition API Pinia、`unplugin-auto-import` 自动导入、`unplugin-vue-components` 组件按需注册、极简 `main.js`、SCSS 变量主题、oxlint | 商城业务页面、静态路由、业务 API |
| **template-vue3** | `CommonMenu` 递归菜单、`CommonTags` 标签页、`CommonHeader` 面包屑、`CommonAside` 侧边栏、`componentMap` 动态路由 | Options API Pinia、异步 bootstrap、`.js`/`.ts` 混用 |

## 技术栈

| 层 | 选型 |
|---|---|
| 框架 | Vue 3.5（Composition API） |
| 构建 | Vite 7 |
| UI | Element Plus 2.12（自动按需引入） |
| 路由 | Vue Router 4（componentMap 动态路由） |
| 状态 | Pinia 3 + pinia-plugin-persistedstate |
| HTTP | Axios（拦截器 + `R<T>` 适配） |
| 样式 | SCSS + Element Plus 主题覆盖 |
| 工具 | @vueuse/core、dayjs |
| 自动导入 | unplugin-auto-import（Vue/Router/Pinia API） |
| 组件注册 | unplugin-vue-components（Element Plus 按需） |
| Lint | oxlint + ESLint |

## 目录结构

```
frontend/
├── src/
│   ├── main.js              # 极简入口：app.use(router).use(pinia).mount('#app')
│   ├── App.vue              # <RouterView />
│   ├── api/                 # API 层（xxxApi.js，默认导出对象）
│   ├── utils/
│   │   └── http.js          # Axios 实例（Token 拦截、R<T> 格式适配、401 跳登录）
│   ├── stores/
│   │   └── app.js           # 全局状态（userInfo/token/menu/tags/折叠）
│   ├── router/
│   │   ├── index.js         # 静态路由 + componentMap + addDynamicFLatRoutes
│   │   └── guard.js         # 鉴权守卫（每次刷新调后端 getUserInfo 拿最新菜单）
│   ├── components/          # 公共组件（CommonAside/Header/Menu/Tags）
│   ├── views/               # 页面
│   │   ├── Login.vue
│   │   ├── Home.vue
│   │   ├── Layout.vue
│   │   ├── 404.vue
│   │   ├── product/         # 分类/品牌/属性/SPU/SKU
│   │   ├── order/           # 订单管理
│   │   └── system/          # 用户/角色/菜单管理
│   └── styles/              # var.scss / common.scss / element主题
```

## 关键设计决策

### 动态路由恢复（业内标准做法）

每次 F5 刷新调 `authApi.getUserInfo()` 从后端拿最新菜单，不用 localStorage 恢复。

```
刷新 → guard → await getUserInfo() → menu → addDynamicFLatRoutes → 重导航
```

优点：权限实时生效、不可篡改、不依赖本地缓存结构。

### 后端接口依赖

| 接口 | 说明 |
|---|---|
| `POST /api/auth/login` | 登录，返回 `{ token, userInfo: { username, roles[], menu[] } }` |
| `GET /api/auth/userInfo` | 获取当前用户信息含菜单（刷新时调用） |
| `POST /api/auth/refresh` | Token 续期 |

### 菜单 JSON 格式

后端返回的 `menu[]` 需与 `componentMap` 的 path 一致：

```json
[
  { "id": "1", "name": "商品管理", "path": "/product", "children": [
    { "id": "11", "name": "分类管理", "path": "/product/category" },
    { "id": "12", "name": "品牌管理", "path": "/product/brand" }
  ]}
]
```

## 开发

```bash
npm install
npm run dev        # http://localhost:8633
npm run build      # 生产构建
npm run lint       # oxlint + eslint
```
