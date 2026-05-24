export const useUserStore = defineStore('user', () => {
  // === 状态 ===
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref({
    username: '',
    avatar: '',
  })

  // === 方法 ===
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

  const logout = () => {
    setToken('')
    userInfo.value = { username: '', avatar: '' }
  }

  return {
    token,
    userInfo,
    setToken,
    setUserInfo,
    logout,
  }
})
