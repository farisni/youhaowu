<template>
  <div class="menu-page">
    <!-- 通用表格 -->
    <CommonTable
      ref="commonTableRef"
      :api="api"
      :columns="columns"
      row-key="id"
      :search-height="75"
      :tree-props="{ children: 'children', checkStrictly: true }"
      @row-dblclick="(row) => hasChildren(row) && tableRef?.toggleRowExpansion(row, undefined)"
    >
      <!-- 搜索区 -->
      <template #search>
        <div class="search-area">
          <el-form>
            <el-row :gutter="10">
              <el-col :span="5">
                <el-form-item label="关键字">
                  <el-input placeholder="系统模块/操作人" v-model="state.searchObj.keyword" @keyup.enter="handleSearch" />
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="请求路径">
                  <el-input v-model="state.searchObj.operUrl" @keyup.enter="handleSearch" />
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="创建时间">
                  <el-date-picker
                    v-model="state.createTimes"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    type="daterange"
                    range-separator="至" />
                </el-form-item>
              </el-col>
              <el-col :span="7" class="btn-col">
                <el-button type="primary" @click="handleSearch">
                  <el-icon><Search /></el-icon><span>搜索</span>
                </el-button>
                <el-button @click="reset">
                  <el-icon><Refresh /></el-icon><span>重置</span>
                </el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>

      <!-- 操作按钮 -->
      <template #operation-buttons>
        <el-button type="success" @click="add()">
          <el-icon><Plus /></el-icon><span>新建</span>
        </el-button>
      </template>

      <!-- 行操作 -->
      <template #actions="{ row }">
        <el-button v-if="row.type === 0 || row.type === 1" type="primary" size="small" link icon="plus" @click="add(row.id)">新建</el-button>
        <el-button type="primary" size="small" link icon="edit" @click="edit(row.id)">编辑</el-button>
      </template>

      <!-- 类型列 -->
      <template #_type="{ row }">
        <el-tag v-if="row.type === 0" type="warning">目录</el-tag>
        <el-tag v-if="row.type === 1" type="success">菜单</el-tag>
        <el-tag v-if="row.type === 2" type="danger">按钮</el-tag>
      </template>

      <!-- 图标列 -->
      <template #_icon="{ row }">
        <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
        <div v-else-if="row.icon" :class="`i-svg:${row.icon}`" class="menu-icon" />
      </template>

      <!-- 状态列 -->
      <template #_status="{ row }">
        <el-switch
          :model-value="row.status === 1"
          inline-prompt active-text="启用" inactive-text="停用"
          style="--el-switch-off-color: gray"
          @change="handleStatusChange(row)" />
      </template>

    </CommonTable>

    <!-- 抽屉编辑 -->
    <el-drawer v-model="state.drawerVisible" direction="rtl" size="35%">
      <template #header><h4>{{ state.drawerTitle }}</h4></template>
      <template #default>
        <el-form :model="state.formData" label-width="100px">
          <el-form-item label="父级菜单">
            <el-tree-select
              v-model="state.formData.parentId"
              :data="menuOptions"
              placeholder="选择上级菜单"
              check-strictly filterable
              :default-expanded-keys="['-1']"
              :render-after-expand="false">
              <template #prefix>
                <span v-if="state.formData.parentId" class="full-path-prefix">
                  {{ getSelectedFullPath(state.formData.parentId) }}
                </span>
              </template>
            </el-tree-select>
          </el-form-item>
          <el-form-item label="菜单类型">
            <el-radio-group v-model="state.formData.type">
              <el-radio :value="0">目录</el-radio>
              <el-radio :value="1">菜单</el-radio>
              <el-radio :value="2">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="菜单名称"><el-input v-model="state.formData.name" /></el-form-item>
          <el-form-item label="排序">
            <el-input-number v-model="state.formData.sortValue" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item label="路由地址" v-if="state.formData.type !== 2">
            <el-input v-model="state.formData.path" placeholder="请输入路由地址" />
          </el-form-item>
          <el-form-item v-if="state.formData.type !== 2" label="图标">
            <IconSelect v-model="state.formData.icon" />
          </el-form-item>
          <el-form-item label="组件路径" v-if="state.formData.type === 1">
            <el-input v-model="state.formData.component" placeholder="system/user/index">
              <template #prepend>src/views/</template>
              <template #append>.vue</template>
            </el-input>
          </el-form-item>
          <el-form-item label="权限字符" v-if="state.formData.type === 2">
            <el-input v-model="state.formData.perms" placeholder="请输入权限标识" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="state.formData.status">
              <el-radio :value="1">正常</el-radio>
              <el-radio :value="0">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <el-button @click="state.drawerVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </template>
    </el-drawer>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import api from "@/api/system/menuApi.js"
import IconSelect from "@/components/IconSelect/index.vue"
import { processDateRange } from '@/utils/dateUtils'
import { ElMessage } from 'element-plus'

const MenuTypeEnum = { CATALOG: 0, MENU: 1, BUTTON: 2 }

const initFormData = {
  id: '', parentId: '', parentName: '', name: '', type: 0,
  path: '', component: '', perms: '', icon: '', sortValue: 1, status: 1,
}

const formData = { ...initFormData }
const menuOptions = ref([])
const tableRef = ref(null)
const commonTableRef = ref(null)

const state = ref({
  createTimes: [],
  searchObj: { keyword: "", operUrl: "", createTimeBegin: "", createTimeEnd: "" },
  formData: { ...formData },
  drawerVisible: false,
  drawerTitle: "",
})

const columns = [
  { type: 'selection', width: 30, 'header-cell-class-name': () => 'no-select-all' },
  { prop: 'name', label: '菜单名称', width: 150 },
  { label: '类型', align: 'center', width: 75, prop: '_type' },
  { label: '图标', prop: '_icon' },
  { prop: 'perms', label: '权限标识', width: 160 },
  { prop: 'path', label: '路由地址', width: 200 },
  { prop: 'component', label: '组件路径', width: 200 },
  { prop: 'sortValue', label: '排序', width: 60 },
  { label: '状态', width: 80, prop: '_status' },
]

const fetchData = async (searchObj) => {
  const timeRange = processDateRange(state.value.createTimes)
  const params = { ...searchObj, createTimeBegin: timeRange[0], createTimeEnd: timeRange[1] }
  commonTableRef.value?.refresh(params)
}

const reset = () => {
  state.value.createTimes = []
  state.value.searchObj = {}
  fetchData({})
}

const add = (id) => {
  state.value.formData = { ...initFormData }
  if (id) state.value.formData.parentId = id
  state.value.drawerVisible = true
  state.value.drawerTitle = "新建菜单"
}

const edit = async (id) => {
  const resp = await api.getById(id)
  state.value.formData = { ...resp.data }
  state.value.drawerTitle = "编辑菜单"
  state.value.drawerVisible = true
}

const saveOrUpdate = async () => {
  const data = state.value.formData
  const resp = data.id ? await api.updateById(data) : await api.save(data)
  ElMessage.success(resp.msg || '操作成功')
  commonTableRef.value?.refresh()
  state.value.drawerVisible = false
}

const handleStatusChange = async (row) => {
  row.status = row.status === 1 ? 0 : 1
  await api.updateById({ id: row.id, status: row.status })
  ElMessage.success(row.status === 1 ? "已启用" : "已停用")
}

const transformData = (data) => {
  return data
    .filter(item => item.type !== 2)
    .map(item => ({
      value: item.id, label: item.name,
      children: item.children ? transformData(item.children) : undefined,
    }))
}

const getSelectedFullPath = (nodeId) => {
  const findPath = (nodes, targetId, path = []) => {
    for (const node of nodes) {
      if (node.value === targetId) return path
      if (node.children) {
        const found = findPath(node.children, targetId, [...path, node.label])
        if (found.length) return found
      }
    }
    return []
  }
  const path = findPath(menuOptions.value, nodeId)
  return path.length ? path.join(' / ') + ' /' : ''
}

const hasChildren = (row) => Array.isArray(row.children) && row.children.length > 0

onMounted(() => {
  api.findNodes().then(resp => {
    menuOptions.value = [{ value: "-1", label: "顶级菜单", children: transformData(resp.data) }]
  })
})

const handleSearch = () => fetchData(state.value.searchObj)

</script>
<style lang="scss" scoped>
.menu-page {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 90px);
  padding: 12px 0 0 0;
}

.search-area {
  .el-form-item { margin: 0; }
  .el-form-item__label { color: #54565a; font-weight: 600; }
  .el-row { padding: 5px 0; }
  .btn-col { display: flex; justify-content: flex-end; align-items: center; gap: 8px; }
}

:deep(.el-drawer__header) {
  margin-bottom: 0;
  padding-bottom: 20px;
  border-bottom: 1px solid var(--el-border-color-light);
}
:deep(.el-form-item__label) { font-weight: bold; }
:deep(.el-table__header .el-checkbox) { display: none; }
</style>
