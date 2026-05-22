import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { title: '登录' },
    },
  ],
  scrollBehavior() {
    return { top: 0 }
  },
})

// 组件映射表：后端菜单 path → 页面组件
const componentMap = {
  '/home': () => import('@/views/Home.vue'),
  '/product/category': () => import('@/views/product/category.vue'),
  '/product/brand': () => import('@/views/product/brand.vue'),
  '/product/attr': () => import('@/views/product/attr.vue'),
  '/product/spu': () => import('@/views/product/spu.vue'),
  '/product/sku': () => import('@/views/product/sku.vue'),
  '/order/list': () => import('@/views/order/index.vue'),
  '/system/user': () => import('@/views/system/userList.vue'),
  '/system/role': () => import('@/views/system/roleList.vue'),
  '/system/menu': () => import('@/views/system/menuList.vue'),
}

// 后端菜单 → 动态平铺注册路由
export const addDynamicFLatRoutes = (menuArr) => {
  const flatten = (items) => {
    const result = []
    items.forEach((item) => {
      const comp = componentMap[item.path]
      if (comp) {
        result.push({
          path: item.path,
          name: item.id || item.path,
          component: comp,
          meta: { title: item.name },
        })
      }
      if (item.children && item.children.length) {
        result.push(...flatten(item.children))
      }
    })
    return result
  }

  const routes = flatten(menuArr)
  routes.forEach((route) => {
    if (!router.hasRoute(route.name)) {
      router.addRoute(route)
    }
  })

  // 404 兜底
  if (!router.hasRoute('404')) {
    router.addRoute({
      path: '/:pathMatch(.*)*',
      name: '404',
      component: () => import('@/views/404.vue'),
      meta: { title: '404' },
    })
  }
}

export const removeDynamicRoutes = () => {
  router.getRoutes().forEach((route) => {
    const name = route.name
    if (name && name !== 'Login' && name !== '404') {
      router.removeRoute(name)
    }
  })
}

export { componentMap }
export default router
