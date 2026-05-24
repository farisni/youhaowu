import router from '@/router/index.js'

router.beforeEach((to, from, next) => {
  // 暂时不做登录拦截，后续按需开启
  next()
})
