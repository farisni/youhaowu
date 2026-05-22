import http from '@/utils/http.js'

export default {
  login(data) {
    return http.post('/api/auth/login', data)
  },
  getUserInfo() {
    return http.get('/api/auth/userInfo')
  },
  refreshToken(refreshToken) {
    return http.post('/api/auth/refresh', { refreshToken })
  },
}
