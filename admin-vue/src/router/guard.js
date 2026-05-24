import router, { addDynamicFLatRoutes } from '@/router/index.js'
import { useAppStore } from '@/stores/app.js'

let menusLoaded = false

router.beforeEach(async (to, from, next) => {
  const appStore = useAppStore()
  const token = appStore.token

  // 未登录 → 跳登录页
  if (!token && to.path !== '/login') {
    menusLoaded = false
    return next('/login')
  }

  // 已登录 → 禁止访问登录页
  if (token && to.path === '/login') {
    return next('/home')
  }

  // 有 token 但路由未加载 → 调后端拿菜单
  if (token && !menusLoaded) {
    try {
      const { default: authApi } = await import('@/api/authApi.js')
      const res = await authApi.getUserInfo()
      appStore.setUserInfo(res.data)
      addDynamicFLatRoutes(res.data.userInfo.menu)
      menusLoaded = true
      return next({ ...to, replace: true })
    } catch {
      appStore.clearPersistedData()
      menusLoaded = false
      return next('/login')
    }
  }

  next()
})
