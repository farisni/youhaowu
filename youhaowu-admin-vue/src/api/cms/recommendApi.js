import http from '@/utils/http.js'

export default {
  page(page, limit, searchObj = {}) {
    const params = { pageNum: page, pageSize: limit }
    if (searchObj.title) params.title = searchObj.title
    if (searchObj.status !== undefined && searchObj.status !== '') params.status = searchObj.status
    return http.post('/api/cms/recommend/page', params)
  },
  getById(id) {
    return http.get(`/api/cms/recommend/info/${id}`)
  },
  /** 新增/编辑统一走 save，id 空=新增，id 非空=更新 */
  save(data) {
    return http.post('/api/cms/recommend/save', data)
  },
  deleteById(id) {
    return http.post('/api/cms/recommend/delete', [id])
  },
}
