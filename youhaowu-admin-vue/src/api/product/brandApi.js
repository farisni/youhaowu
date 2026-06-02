import http from '@/utils/http.js'

export default {
  /** 分页列表 */
  page(page, limit, searchObj = {}) {
    const params = { pageNum: page, pageSize: limit }
    if (searchObj.key) params.keyword = searchObj.key
    if (searchObj.showStatus !== undefined && searchObj.showStatus !== '') params.showStatus = searchObj.showStatus
    return http.get('/api/product/brand/list', { params })
  },

  /** 详情 */
  getById(id) {
    return http.get(`/api/product/brand/info/${id}`)
  },

  /** 新增 */
  save(data) {
    return http.post('/api/product/brand/save', data)
  },

  /** 更新 */
  updateById(id, data) {
    return http.post(`/api/product/brand/update/${id}`, data)
  },

  /** 批量删除 */
  deleteBatch(ids) {
    return http.post('/api/product/brand/delete/batch', ids)
  },

  /** 删除 */
  deleteById(id) {
    return http.post(`/api/product/brand/delete/${id}`)
  },
}
