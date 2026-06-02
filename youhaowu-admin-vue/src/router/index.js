import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: () => import('@/views/Layout.vue'),
      redirect: '/home',
      children: [],
    },
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

// 组件映射表
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
  '/cms/banner': () => import('@/views/cms/banner.vue'),
  '/cms/news': () => import('@/views/cms/news.vue'),
  '/cms/recommend': () => import('@/views/cms/recommend.vue'),}

export const addDynamicFLatRoutes = (menuArr) => {
  const flatten = (items) => {
    const result = []
    items.forEach((item) => {
      if (item.path === '/home') return
      const comp = componentMap[item.path]
      if (comp) {
        result.push({
          path: item.path,
          name: item.path,
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

  // 为父级菜单注册路由（用于面包屑匹配），重定向到第一个子菜单
  const registerParents = (items) => {
    items.forEach((item) => {
      if (item.children && item.children.length && item.path !== '/product' && item.path !== '/order' && item.path !== '/system') return // skip root
      if (item.children && item.children.length) {
        if (!router.hasRoute(item.path)) {
          router.addRoute('Layout', {
            path: item.path,
            name: item.path,
            redirect: item.children[0].path,
            meta: { title: item.name },
          })
        }
        registerParents(item.children)
      }
    })
  }
  registerParents(menuArr)

  // 先添加 /home
  if (!router.hasRoute('/home')) {
    router.addRoute('Layout', {
      path: '/home',
      name: '/home',
      component: () => import('@/views/Home.vue'),
      meta: { title: '首页' },
    })
  }

  routes.forEach((route) => {
    if (!router.hasRoute(route.name)) {
      router.addRoute('Layout', route)
    }
  })

  if (!router.hasRoute('404')) {
    router.addRoute('Layout', {
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
    if (name && name !== 'Login' && name !== 'Layout' && name !== '404') {
      router.removeRoute(name)
    }
  })
}

export { componentMap }
export default router
