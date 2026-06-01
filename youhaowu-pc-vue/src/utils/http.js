import axios from 'axios'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import router from '@/router/index.js'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '',
  timeout: 10000,
})

// 请求拦截器
http.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

// 响应拦截器
http.interceptors.response.use(
  (res) => {
    const data = res.data
    if (data.code === 0) {
      return data
    }
    ElMessage.error(data.msg || '请求失败')
    return Promise.reject(new Error(data.msg || 'Error'))
  },
  (error) => {
    if (error.response) {
      const { status, data } = error.response
      switch (status) {
        case 401: {
          localStorage.removeItem('token')
          router.push('/login')
          ElMessage.error('登录已过期，请重新登录')
          break
        }
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 500:
          ElMessage.error(data.msg || '服务器内部错误')
          break
        default:
          ElMessage.error(data.msg || '请求失败')
      }
    } else {
      ElMessage.error('网络连接失败')
    }
    return Promise.reject(error)
  },
)

export default http
