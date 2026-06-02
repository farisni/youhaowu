import http from '@/utils/http.js'

export default {
  page(page, limit, searchObj = {}) {
    const params = { pageNum: page, pageSize: limit }
    if (searchObj.tabType) params.tabType = searchObj.tabType
    if (searchObj.status !== undefined && searchObj.status !== '') params.status = searchObj.status
    return http.post('/api/cms/rank/page', params)
  },
  getById(id) {
    return http.get(`/api/cms/rank/info/${id}`)
  },
  save(data) {
    return http.post('/api/cms/rank/save', data)
  },
  deleteById(id) {
    return http.post('/api/cms/rank/delete', [id])
  },
}
