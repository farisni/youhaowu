import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/home/index.vue'),
      meta: { title: '首页' },
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { title: '登录', hideLayout: true },
    },
    {
      path: '/search',
      name: 'Search',
      component: () => import('@/views/Search.vue'),
      meta: { title: '搜索' },
    },
  ],
  scrollBehavior() {
    return { top: 0 }
  },
})

export default router
