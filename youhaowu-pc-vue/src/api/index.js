import http from '@/utils/http.js'
import bannerData from '@/mock/banner.json'
import floorData from '@/mock/floor.json'
// import categoryData from '@/mock/category.json'

const isMock = import.meta.env.DEV

export default {
  // Banner 轮播数据
  getBannerList() {
    if (isMock) return Promise.resolve({ code: 200, data: bannerData })
    return http.get('/api/home/banner')
  },
  // 楼层数据
  getFloorList() {
    if (isMock) return Promise.resolve({ code: 200, data: floorData })
    return http.get('/api/home/floor')
  },
  // 分类数据
  getCategoryList() {
    return http.get('/api/product/category/list/tree')
  },
}
