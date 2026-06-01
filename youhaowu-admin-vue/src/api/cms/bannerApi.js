import http from '@/utils/http.js'

export default {
  page(page, limit, searchObj = {}) {
    const params = { pageNum: page, pageSize: limit }
    if (searchObj.title) params.title = searchObj.title
    if (searchObj.status !== undefined && searchObj.status !== '') params.status = searchObj.status
    return http.get('/api/cms/banner/page', { params })
  },
  getById(id) {
    return http.get(`/api/cms/banner/info/${id}`)
  },
  save(data) {
    return http.post('/api/cms/banner/save', data)
  },
  updateById(id, data) {
    return http.post(`/api/cms/banner/update/${id}`, data)
  },
  deleteById(id) {
    return http.post("/api/cms/banner/delete", [id])
  },
}
