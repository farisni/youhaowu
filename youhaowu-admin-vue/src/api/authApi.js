import http from '@/utils/http.js'

// Mock 数据
const mockMenu = [
  { id: '0', name: '首页', path: '/home', icon: 'HomeFilled' },
  { id: '1', name: '商品管理', path: '/product', icon: 'Goods', children: [
    { id: '11', name: '分类管理', path: '/product/category' },
    { id: '12', name: '品牌管理', path: '/product/brand' },
    { id: '13', name: '属性管理', path: '/product/attr' },
    { id: '14', name: 'SPU管理', path: '/product/spu' },
    { id: '15', name: 'SKU管理', path: '/product/sku' },
  ]},
  { id: '2', name: '订单管理', path: '/order', icon: 'Tickets', children: [
    { id: '21', name: '订单列表', path: '/order/list' },
  ]},
  { id: '3', name: '系统管理', path: '/system', icon: 'Setting', children: [
    { id: '31', name: '用户管理', path: '/system/user' },
    { id: '32', name: '角色管理', path: '/system/role' },
    { id: '33', name: '菜单管理', path: '/system/menu' },
  ]},
  { id: '4', name: '内容管理', path: '/cms', icon: 'Document', children: [
    { id: '41', name: '轮播图管理', path: '/cms/banner' },
    { id: '42', name: '首页快报', path: '/cms/news' },
  ]},
]

const mockUserInfo = {
  username: '管理员',
  avatar: '',
  roles: ['admin'],
}

const mockSuccess = () => ({
  code: 200,
  data: {
    token: 'mock-jwt-token-admin',
    userInfo: { ...mockUserInfo, menu: mockMenu },
  },
})

const devMock = {
  login(data) {
    if (data.username === 'admin' && data.password === '123456') return mockSuccess()
    if (data.phone && data.smsCode === '000000') return mockSuccess()
    return Promise.reject(new Error('账号或密码错误'))
  },
  getUserInfo() {
    const token = localStorage.getItem('token')
    if (token === 'mock-jwt-token-admin') return mockSuccess()
    return Promise.reject(new Error('Token 无效'))
  },
}

const isMock = import.meta.env.DEV

export default {
  login(data) {
    if (isMock) return Promise.resolve(devMock.login(data))
    return http.post('/api/auth/login', data)
  },
  getUserInfo() {
    if (isMock) return Promise.resolve(devMock.getUserInfo())
    return http.get('/api/auth/userInfo')
  },
  refreshToken(refreshToken) {
    return http.post('/api/auth/refresh', { refreshToken })
  },
}
