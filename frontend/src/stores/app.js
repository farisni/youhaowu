export const useAppStore = defineStore('app', () => {
  // === 状态 ===
  const isCollapse = ref(false)
  const activePath = ref('')
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref({
    username: '',
    avatar: '',
    roles: [],
    menu: [],
  })
  const tagsData = ref([
    { id: '1', path: '/home', name: '首页' },
  ])

  // === 方法 ===
  const collapseMenu = () => {
    isCollapse.value = !isCollapse.value
  }

  const setToken = (val) => {
    token.value = val
    if (val) {
      localStorage.setItem('token', val)
    } else {
      localStorage.removeItem('token')
    }
  }

  const setUserInfo = (info) => {
    userInfo.value = { ...userInfo.value, ...info }
  }

  const addTag = (menuItem) => {
    const index = tagsData.value.findIndex((t) => t.path === menuItem.path)
    if (index === -1) {
      tagsData.value.push({
        id: menuItem.id || menuItem.path,
        path: menuItem.path,
        name: menuItem.name,
      })
    }
  }

  const removeTag = (tag) => {
    const index = tagsData.value.findIndex((t) => t.path === tag.path)
    tagsData.value.splice(index, 1)
  }

  const clearPersistedData = () => {
    setToken('')
    tagsData.value = [{ id: '1', path: '/home', name: '首页' }]
    userInfo.value = { username: '', avatar: '', roles: [], menu: [] }
  }

  return {
    isCollapse,
    activePath,
    token,
    userInfo,
    tagsData,
    collapseMenu,
    setToken,
    setUserInfo,
    addTag,
    removeTag,
    clearPersistedData,
  }
}, {
  persist: {
    key: 'app-store',
    storage: localStorage,
    pick: ['isCollapse', 'activePath', 'userInfo'],
  },
})
