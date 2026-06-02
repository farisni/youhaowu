import http from '@/utils/http.js'

export default {
  /** 获取 MinIO presigned PUT URL + accessUrl */
  getPolicy(fileName) {
    return http.get('/api/thirdparty/oss/policy', { params: { fileName } })
  }
}
