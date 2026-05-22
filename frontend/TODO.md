# Admin 前端待办

## 已知问题

- [x] **F5 刷新后动态路由丢失**：已采用业内标准做法——每次刷新调 `authApi.getUserInfo()` 重新获取菜单，不用 localStorage 恢复
- [x] **动态路由恢复后需重导航**：`addDynamicFLatRoutes` 后已加 `next({ ...to, replace: true })`
- [x] **componentMap 路径**：已用全路径做 key（如 `/product/category`）

## 后端接口依赖

- [ ] `POST /api/auth/login` — 返回 `{ token, userInfo: { username, roles[], menu[] } }`
- [ ] `GET /api/auth/userInfo` — 返回当前用户信息含菜单（guard 刷新时调用）
- [ ] `POST /api/auth/refresh` — Token 续期

## Phase 3 — 布局组件（来自 template-vue3）

- [ ] `Layout.vue`：el-container 布局（`el-aside` + `el-header` + CommonTags + `el-main`）
- [ ] `CommonAside.vue`：侧边栏（Logo + `CommonMenu`，读 `appStore.userInfo.menu`）
- [ ] `CommonMenu.vue`：递归菜单组件（支持 el-icon + SVG icon，点击 emit `menu-item-click`）
- [ ] `CommonHeader.vue`：顶栏（折叠按钮 + 面包屑 + 用户下拉退出）
- [ ] `CommonTags.vue`：标签页导航（点击切换路由，关闭跳相邻标签）

## Phase 4 — 登录对接

- [ ] `Login.vue`：对接 auth-admin 登录接口，返回 `{ token, userInfo: { menu[] } }`
- [ ] 登录成功后：`setToken` → `setUserInfo` → `addDynamicFLatRoutes` → 跳 `/home`

## Phase 5 — 业务页面

- [ ] 分类管理（对接已有接口）
- [ ] 品牌/属性/SPU/SKU 管理
- [ ] 订单管理
- [ ] 用户管理
- [ ] 角色权限管理
- [ ] 菜单管理
- [ ] 仪表盘
