<template>
  <div class="brand-page">
    <CommonTable
      ref="tableRef"
      :api="api"
      :columns="columns"
      row-key="brandId"
      :actions-width="160"
    >
      <template #search>
        <div class="search-area">
          <el-form>
            <el-row :gutter="10">
              <el-col :span="5">
                <el-form-item label="关键字">
                  <el-input v-model="searchObj.key" placeholder="品牌名称/首字母" @keyup.enter="handleSearch" />
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="状态">
                  <el-select v-model="searchObj.showStatus" placeholder="全部" clearable>
                    <el-option label="显示" :value="1" />
                    <el-option label="隐藏" :value="0" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="7" class="btn-col">
                <el-button type="primary" @click="handleSearch"><el-icon><Search /></el-icon>搜索</el-button>
                <el-button @click="resetSearch"><el-icon><Refresh /></el-icon>重置</el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>
      <template #operation-buttons>
        <el-button type="success" @click="add"><el-icon><Plus /></el-icon>新建</el-button>
      </template>
      <template #logo="{ row }">
        <img v-if="row.logo" :src="row.logo" style="width:40px;height:40px;object-fit:contain" />
        <span v-else class="no-logo">—</span>
      </template>
      <template #showStatus="{ row }">
        <el-switch :model-value="row.showStatus===1" inline-prompt active-text="显示" inactive-text="隐藏"
          style="--el-switch-off-color:gray" @change="handleStatusChange(row)" />
      </template>
      <template #actions="{ row }">
        <el-button type="primary" size="small" link :icon="Edit" @click="edit(row.brandId)">编辑</el-button>
        <el-button type="danger" size="small" link :icon="Delete" @click="remove(row)">删除</el-button>
      </template>
    </CommonTable>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" :close-on-click-modal="false">
      <el-form :model="form" label-width="100px">
        <el-form-item label="品牌名称">
          <el-input v-model="form.name" placeholder="请输入品牌名称" />
        </el-form-item>
        <el-form-item label="Logo URL">
          <el-input v-model="form.logo" placeholder="请输入 Logo 图片地址" />
        </el-form-item>
        <el-form-item label="品牌介绍">
          <el-input v-model="form.descript" type="textarea" :rows="3" placeholder="请输入品牌介绍" />
        </el-form-item>
        <el-form-item label="首字母">
          <el-input v-model="form.firstLetter" placeholder="检索首字母，如 H" maxlength="1" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="显示状态">
          <el-select v-model="form.showStatus" style="width:100%">
            <el-option label="显示" :value="1" />
            <el-option label="隐藏" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import CommonTable from '@/components/CommonTable.vue'
import api from '@/api/product/brandApi.js'

const tableRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const searchObj = reactive({ key: '', showStatus: '' })

const initForm = { name: '', logo: '', descript: '', firstLetter: '', sort: 0, showStatus: 1 }
const form = reactive({ ...initForm })

const columns = [
  { prop: 'brandId', label: '品牌ID', width: 80 },
  { prop: 'name', label: '品牌名称', minWidth: 150 },
  { label: 'Logo', width: 80, prop: 'logo' },
  { prop: 'firstLetter', label: '首字母', minWidth: 80 },
  { prop: 'sort', label: '排序', minWidth: 80 },
  { label: '状态', width: 90, prop: 'showStatus' },
]

const handleSearch = () => tableRef.value?.refresh({ ...searchObj })
const resetSearch = () => { searchObj.key = ''; searchObj.showStatus = ''; handleSearch() }

const add = () => {
  isEdit.value = false; editId.value = null
  dialogTitle.value = '新建品牌'
  Object.assign(form, initForm)
  dialogVisible.value = true
}

const edit = async (id) => {
  isEdit.value = true; editId.value = id
  dialogTitle.value = '编辑品牌'
  const res = await api.getById(id)
  if (res.data) Object.assign(form, res.data)
  dialogVisible.value = true
}

const submitForm = async () => {
  if (!form.name) { ElMessage.warning('请输入品牌名称'); return }
  if (isEdit.value) {
    await api.updateById(editId.value, form)
    ElMessage.success('更新成功')
  } else {
    await api.save(form)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  tableRef.value?.refresh()
}

const remove = (row) => {
  ElMessageBox.confirm(`确定删除品牌【${row.name}】？`, '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',
  }).then(async () => {
    await api.deleteById(row.brandId)
    ElMessage.success('删除成功')
    tableRef.value?.refresh()
  }).catch(() => {})
}

const handleStatusChange = async (row) => {
  row.showStatus = row.showStatus === 1 ? 0 : 1
  await api.updateById(row.brandId, { showStatus: row.showStatus })
  ElMessage.success(row.showStatus === 1 ? '已显示' : '已隐藏')
}
</script>

<style lang="scss" scoped>
.brand-page { display: flex; flex-direction: column; height: calc(100vh - 90px); padding: 12px 0 0 0; }
.search-area { .el-form-item { margin: 0; } .el-row { padding: 5px 0; } .btn-col { display: flex; justify-content: flex-end; align-items: center; gap: 8px; } }
.no-logo { color: #ccc; font-size: 14px; }
</style>
