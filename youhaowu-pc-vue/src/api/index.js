import http from '@/utils/http.js'
import floorData from '@/mock/floor.json'

const isMock = import.meta.env.DEV

export default {
  // Banner 轮播数据（从 CMS 获取）
  getBannerList() {
    return http.get('/api/cms/banner/list')
  },
  // 楼层数据
  getFloorList() {
    if (isMock) return Promise.resolve({ code: 200, data: floorData })
    return http.get('/api/home/floor')
  },
  // 好物快报
  getNewsList() {
    return http.get('/api/cms/news/list')
  },
  // 品牌 TOP
  getBrandTop(limit = 10) {
    return http.get('/api/product/brand/list', { params: { pageNum: 1, pageSize: limit, sortField: 'sort', sortAsc: true } })
  },
  // 分类数据
  getCategoryList() {
    return http.get('/api/product/category/list/tree')
  },
}
