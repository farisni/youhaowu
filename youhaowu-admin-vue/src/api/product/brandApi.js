import http from '@/utils/http.js'

const isMock = import.meta.env.DEV

// Mock 数据
let _mockId = 100
const nextId = () => ++_mockId

const _mockList = [
  { brandId: 1, name: '华为', logo: 'https://img.alicdn.com/imgextra/i4/O1CN01v0Fqh41qf9XJFB0qb_!!6000000005521-2-tps-200-200.png', descript: '华为技术有限公司', showStatus: 1, firstLetter: 'H', sort: 1 },
  { brandId: 2, name: '小米', logo: 'https://img.alicdn.com/tfs/TB1e.U2hOME.eBjSZFNXXc7mpXa-200-200.png', descript: '小米科技有限公司', showStatus: 1, firstLetter: 'X', sort: 2 },
  { brandId: 3, name: '苹果', logo: 'https://img.alicdn.com/tfs/TB1e.U2hOMfgKJjSZFNXXc7mpXa-200-200.png', descript: 'Apple Inc.', showStatus: 1, firstLetter: 'A', sort: 3 },
  { brandId: 4, name: 'OPPO', logo: '', descript: 'OPPO广东移动通信有限公司', showStatus: 0, firstLetter: 'O', sort: 4 },
  { brandId: 5, name: 'vivo', logo: '', descript: '维沃移动通信有限公司', showStatus: 1, firstLetter: 'V', sort: 5 },
]

// 对象转 URL 参数
const toParams = (searchObj) => {
  const params = {}
  if (searchObj?.key) params.key = searchObj.key
  if (searchObj?.showStatus !== undefined && searchObj?.showStatus !== '') params.showStatus = searchObj.showStatus
  return params
}

export default {
  /** 分页列表 */
  page(page, limit, searchObj = {}) {
    if (isMock) {
      let filtered = [..._mockList]
      if (searchObj.key) filtered = filtered.filter(r => r.name.includes(searchObj.key) || r.firstLetter?.toUpperCase().includes(searchObj.key.toUpperCase()))
      if (searchObj.showStatus !== undefined && searchObj.showStatus !== '') filtered = filtered.filter(r => r.showStatus === Number(searchObj.showStatus))
      const start = (page - 1) * limit
      return Promise.resolve({
        code: 200,
        data: { list: filtered.slice(start, start + limit), total: filtered.length },
        msg: 'ok',
      })
    }
    return http.get('/api/product/brand/list', { params: { page, limit, ...toParams(searchObj) } })
  },

  /** 详情 */
  getById(id) {
    if (isMock) {
      const found = _mockList.find(r => r.brandId === id)
      return Promise.resolve({ code: 200, data: found || null, msg: 'ok' })
    }
    return http.get(`/product/brand/info/${id}`)
  },

  /** 新增 */
  save(data) {
    if (isMock) {
      const item = { ...data, brandId: nextId() }
      _mockList.unshift(item)
      return Promise.resolve({ code: 200, data: item, msg: '新增成功' })
    }
    return http.post('/api/product/brand/save', data)
  },

  /** 更新 */
  updateById(id, data) {
    if (isMock) {
      const idx = _mockList.findIndex(r => r.brandId === id)
      if (idx > -1) _mockList[idx] = { ..._mockList[idx], ...data }
      return Promise.resolve({ code: 200, msg: '更新成功' })
    }
    return http.post(`/product/brand/update/${id}`, data)
  },

  /** 删除 */
  deleteById(id) {
    if (isMock) {
      const idx = _mockList.findIndex(r => r.brandId === id)
      if (idx > -1) _mockList.splice(idx, 1)
      return Promise.resolve({ code: 200, msg: '删除成功' })
    }
    return http.post(`/product/brand/delete/${id}`)
  },
}
