<template>
  <div class="cms-page">
    <CommonTable ref="tableRef" :api="api" :columns="columns" row-key="id" :actions-width="140">
      <template #search>
        <el-form :inline="true">
          <el-form-item label="类型">
            <el-select v-model="searchObj.tabType" placeholder="全部" clearable style="width:120px">
              <el-option label="热卖排行" value="hot" />
              <el-option label="特价排行" value="sale" />
              <el-option label="新品排行" value="new" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchObj.status" placeholder="全部" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch"><el-icon><Search /></el-icon>搜索</el-button>
            <el-button @click="resetSearch"><el-icon><Refresh /></el-icon>重置</el-button>
          </el-form-item>
        </el-form>
      </template>
      <template #operation-buttons>
        <el-button type="success" @click="add"><el-icon><Plus /></el-icon>新建</el-button>
      </template>
      <template #tabType="{ row }">
        <el-tag :type="row.tabType==='hot'?'danger':row.tabType==='sale'?'warning':''" size="small">
          {{ row.tabType==='hot'?'热卖':row.tabType==='sale'?'特价':'新品' }}
        </el-tag>
      </template>
      <template #imgUrl="{ row }">
        <el-popover v-if="row.imgUrl" placement="top" :width="300" trigger="hover">
          <template #reference>
            <img :src="row.imgUrl" style="width:80px;height:40px;object-fit:cover;border-radius:4px;cursor:pointer" />
          </template>
          <img :src="row.imgUrl" style="width:100%" />
        </el-popover>
        <span v-else style="color:#ccc">—</span>
      </template>
      <template #urlText="{ row }">
        <span style="font-size:12px;color:#666;word-break:break-all">{{ row.imgUrl }}</span>
      </template>
      <template #status="{ row }">
        <el-switch :model-value="row.status===1" inline-prompt active-text="启用" inactive-text="禁用"
          style="--el-switch-off-color:gray" @change="handleStatusChange(row)" />
      </template>
      <template #actions="{ row }">
        <el-button type="primary" size="small" link :icon="Edit" @click="edit(row.id)">编辑</el-button>
        <el-button type="danger" size="small" link :icon="Delete" @click="remove(row)">删除</el-button>
      </template>
    </CommonTable>

    <el-dialog v-model="dialogVisible" @keyup.enter="submitForm" :title="dialogTitle" width="500px" :close-on-click-modal="false">
      <el-form :model="form" label-width="100px">
        <el-form-item label="类型">
          <el-select v-model="form.tabType" style="width:100%">
            <el-option label="热卖排行" value="hot" />
            <el-option label="特价排行" value="sale" />
            <el-option label="新品排行" value="new" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品标题">
          <el-input v-model="form.title" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            drag
            :auto-upload="false"
            :show-file-list="false"
            :on-change="handleFileChange"
            accept="image/*"
            style="width:100%"
          >
            <template v-if="form.imgUrl">
              <img :src="form.imgUrl" style="width:100%;max-height:180px;object-fit:contain;border-radius:4px" />
              <div style="margin-top:8px;color:#999;font-size:13px">拖拽或点击更换图片</div>
            </template>
            <template v-else>
              <el-icon style="font-size:48px;color:#c0c4cc"><UploadFilled /></el-icon>
              <div style="margin-top:12px;color:#999">拖拽图片到此处或点击上传</div>
            </template>
          </el-upload>
          <div v-if="form.imgUrl" style="margin-top:6px">
            <el-input v-model="form.imgUrl" readonly size="small" placeholder="上传后自动填入" />
          </div>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.price" placeholder="如 ¥8999.00" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width:100%">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
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
import { Search, Refresh, Plus, Edit, Delete, UploadFilled } from '@element-plus/icons-vue'
import CommonTable from '@/components/CommonTable.vue'
import api from '@/api/cms/rankApi.js'
import ossApi from '@/api/thirdparty/ossApi.js'

const tableRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const searchObj = reactive({ tabType: '', status: '' })

const initForm = { tabType: 'hot', title: '', imgUrl: '', fileName: '', price: '', sort: 0, status: 1 }
const form = reactive({ ...initForm })

const columns = [
  { prop: 'id', label: 'ID', width: 60 },
  { label: '类型', width: 80, prop: 'tabType' },
  { prop: 'title', label: '商品标题', minWidth: 160 },
  { label: '图片', width: 100, prop: 'imgUrl' },
  { prop: 'urlText', label: '图片 URL', minWidth: 200 },
  { prop: 'price', label: '价格', width: 100 },
  { prop: 'sort', label: '排序', width: 70 },
  { label: '状态', width: 90, prop: 'status' },
]

const handleSearch = () => tableRef.value?.refresh({ ...searchObj })
const resetSearch = () => { searchObj.tabType = ''; searchObj.status = ''; handleSearch() }

const add = () => {
  isEdit.value = false; editId.value = null
  dialogTitle.value = '新建排行'
  Object.assign(form, initForm)
  dialogVisible.value = true
}

const edit = async (id) => {
  isEdit.value = true; editId.value = id
  dialogTitle.value = '编辑排行'
  const res = await api.getById(id)
  if (res.data) Object.assign(form, res.data)
  dialogVisible.value = true
}

const handleFileChange = async (file) => {
  try {
    const policyRes = await ossApi.getPolicy(file.name)
    const { url, accessUrl } = policyRes.data
    await fetch(url, {
      method: 'PUT',
      body: file.raw,
      headers: { 'Content-Type': file.raw.type || 'application/octet-stream' }
    })
    form.imgUrl = accessUrl
    form.fileName = file.name
    ElMessage.success('上传成功')
  } catch (e) {
    ElMessage.error('上传失败: ' + (e.message || '网络错误'))
  }
}

const submitForm = async () => {
  if (!form.title) { ElMessage.warning('请输入标题'); return }
  await api.save({ id: editId.value || undefined, ...form })
  ElMessage.success('保存成功')
  dialogVisible.value = false
  tableRef.value?.refresh()
}

const remove = (row) => {
  ElMessageBox.confirm(`确定删除排行【${row.title}】？`, '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',
  }).then(async () => {
    await api.deleteById(row.id)
    ElMessage.success('删除成功')
    tableRef.value?.refresh()
  }).catch(() => {})
}

const handleStatusChange = async (row) => {
  row.status = row.status === 1 ? 0 : 1
  await api.save({ id: row.id, status: row.status })
  ElMessage.success(row.status === 1 ? '已启用' : '已禁用')
}
</script>

<style lang="scss" scoped>
.cms-page { display: flex; flex-direction: column; height: calc(100vh - 90px); padding: 12px 0 0 0; }
</style>
