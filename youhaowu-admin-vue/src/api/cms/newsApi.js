import http from '@/utils/http.js'

export default {
  page(page, limit, searchObj = {}) {
    const params = { pageNum: page, pageSize: limit }
    if (searchObj.title) params.title = searchObj.title
    if (searchObj.status !== undefined && searchObj.status !== '') params.status = searchObj.status
    return http.post('/api/cms/news/page', params)
  },
  getById(id) {
    return http.get(`/api/cms/news/info/${id}`)
  },
  save(data) {
    return http.post('/api/cms/news/save', data)
  },
  updateById(id, data) {
    return http.post(`/api/cms/news/update/${id}`, data)
  },
  deleteById(id) {
    return http.post("/api/cms/news/delete", [id])
  },
}
