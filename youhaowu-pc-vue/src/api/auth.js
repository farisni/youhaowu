import http from '@/utils/http.js'

const mockUserInfo = {
  username: '测试用户',
  avatar: '',
}

const devMock = {
  login(data) {
    if (data.username === 'faris' && data.password === '123456') {
      return {
        code: 200,
        data: {
          token: 'mock-jwt-token-pc',
          userInfo: mockUserInfo,
        },
      }
    }
    return Promise.reject(new Error('账号或密码错误'))
  },
}

const isMock = import.meta.env.DEV

export default {
  login(data) {
    if (isMock) return Promise.resolve(devMock.login(data))
    return http.post('/api/auth/login', data)
  },
  getUserInfo() {
    return http.get('/api/auth/userInfo')
  },
}
