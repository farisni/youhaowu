import request from '@/utils/http.js'

const api_name = '/admin/system/sysMenu'

// ========== Mock 数据 ==========
let _mockId = 100
const nextId = () => String(++_mockId)

const _mockTree = [
  {
    id: '1', parentId: '-1', name: '系统管理', type: 0, path: '/system',
    icon: 'Setting', perms: '', component: '', sortValue: 1, status: 1,
    createTime: '2026-05-20 10:00:00',
    children: [
      {
        id: '2', parentId: '1', name: '用户管理', type: 1, path: '/system/user',
        icon: 'User', perms: 'system:user', component: 'views/system/userList',
        sortValue: 1, status: 1, createTime: '2026-05-20 10:00:00',
      },
      {
        id: '3', parentId: '1', name: '角色管理', type: 1, path: '/system/role',
        icon: 'Avatar', perms: 'system:role', component: 'views/system/roleList',
        sortValue: 2, status: 1, createTime: '2026-05-20 10:00:00',
      },
      {
        id: '4', parentId: '1', name: '菜单管理', type: 1, path: '/system/menu',
        icon: 'Menu', perms: 'system:menu', component: 'views/system/menuList',
        sortValue: 3, status: 1, createTime: '2026-05-20 10:00:00',
      },
    ],
  },
  {
    id: '5', parentId: '-1', name: '商品管理', type: 0, path: '/product',
    icon: 'Goods', perms: '', component: '', sortValue: 2, status: 1,
    createTime: '2026-05-20 10:00:00',
    children: [
      {
        id: '6', parentId: '5', name: '分类管理', type: 1, path: '/product/category',
        icon: 'Grid', perms: 'product:category', component: 'views/product/category',
        sortValue: 1, status: 1, createTime: '2026-05-20 10:00:00',
      },
    ],
  },
]

// 拍平树（用于分页搜索模拟）
const flattenTree = (nodes, result = []) => {
  nodes.forEach(n => {
    const { children, ...rest } = n
    result.push(rest)
    if (children) flattenTree(children, result)
  })
  return result
}

let _flatData = flattenTree(JSON.parse(JSON.stringify(_mockTree)))

// 重建树（分页返回时用）
const buildTree = (flatList, parentId = '-1') => {
  return flatList
    .filter(item => item.parentId === parentId)
    .map(item => ({
      ...item,
      children: buildTree(flatList, item.id),
    }))
    .map(item => item.children.length === 0 ? { ...item, children: undefined } : item)
}

const isMock = import.meta.env.DEV

// 从拍平数据中搜索
const searchFlat = (keyword, operUrl) => {
  let result = _flatData
  if (keyword) result = result.filter(r => r.name?.includes(keyword))
  if (operUrl) result = result.filter(r => r.path?.includes(operUrl) || r.perms?.includes(operUrl))
  return result
}

export default {
  /* 分页获取菜单列表 */
  page(page, limit, searchObj = {}) {
    if (isMock) {
      const filtered = searchFlat(searchObj.keyword, searchObj.operUrl)
      const start = (page - 1) * limit
      const paginated = filtered.slice(start, start + limit)
      const tree = buildTree(JSON.parse(JSON.stringify(paginated)))
      return Promise.resolve({ code: 200, data: { list: tree, total: filtered.length }, msg: 'ok' })
    }
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj,
    })
  },

  /* 获取树形节点（用于父级菜单选择器） */
  findNodes() {
    if (isMock) {
      return Promise.resolve({ code: 200, data: JSON.parse(JSON.stringify(_mockTree)), msg: 'ok' })
    }
    return request({ url: `${api_name}/findNodes`, method: 'get' })
  },

  /* 根据 ID 获取详情 */
  getById(id) {
    if (isMock) {
      const found = _flatData.find(r => r.id === id)
      return Promise.resolve({ code: 200, data: found || null, msg: 'ok' })
    }
    return request({ url: `${api_name}/getInfo/${id}`, method: 'get' })
  },

  /* 新增 */
  save(data) {
    if (isMock) {
      const item = { ...data, id: nextId(), createTime: new Date().toLocaleString('zh-CN') }
      _flatData.push(item)
      return Promise.resolve({ code: 200, data: item, msg: '新增成功' })
    }
    return request({ url: `${api_name}/save`, method: 'post', data })
  },

  /* 更新 */
  updateById(data) {
    if (isMock) {
      const idx = _flatData.findIndex(r => r.id === data.id)
      if (idx > -1) _flatData[idx] = { ..._flatData[idx], ...data }
      return Promise.resolve({ code: 200, data, msg: '更新成功' })
    }
    return request({ url: `${api_name}/update`, method: 'put', data })
  },

  /* 获取角色菜单 */
  getMenuByRoleId(roleId) {
    return request({ url: `${api_name}/toAssign/${roleId}`, method: 'get' })
  },

  /* 给角色授权菜单 */
  saveRoleMenu(roleMenuVo) {
    return request({ url: `${api_name}/doAssign`, method: 'post', data: roleMenuVo })
  },
}
