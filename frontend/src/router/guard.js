import router, { addDynamicFLatRoutes } from '@/router/index.js'
import { useAppStore } from '@/stores/app.js'

let menusLoaded = false

router.beforeEach(async (to, from, next) => {
  const appStore = useAppStore()

  // 开发模式快捷入口
  if (import.meta.env.DEV && to.path === '/dev') {
    appStore.setToken('mock-jwt-token-admin')
    const { default: authApi } = await import('@/api/authApi.js')
    const res = await authApi.getUserInfo()
    appStore.setUserInfo(res.data)
    addDynamicFLatRoutes(res.data.userInfo.menu)
    menusLoaded = true
    return next('/home')
  }

  const token = appStore.token

  if (!token && to.path !== '/login') {
    menusLoaded = false
    return next('/login')
  }

  if (token && to.path === '/login') {
    return next('/home')
  }

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
