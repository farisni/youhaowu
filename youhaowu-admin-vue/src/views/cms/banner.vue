<template>
  <div class="cms-page">
    <CommonTable ref="tableRef" :api="api" :columns="columns" row-key="id" :actions-width="140">
      <template #search>
        <el-form :inline="true">
          <el-form-item label="标题">
            <el-input v-model="searchObj.title" placeholder="轮播图标题" @keyup.enter="handleSearch" />
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
      <template #urlText="{ row }">
        <span style="font-size:12px;color:#666;word-break:break-all">{{ row.imgUrl }}</span>
      </template>
      <template #imgUrl="{ row }">
        <img v-if="row.imgUrl" :src="row.imgUrl" style="width:80px;height:40px;object-fit:cover;border-radius:4px" />
        <span v-else style="color:#ccc">—</span>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" :close-on-click-modal="false">
      <el-form :model="form" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="轮播图标题" />
        </el-form-item>
        <el-form-item label="图片">
          <div style="display:flex;align-items:center;gap:8px">
            <el-input v-model="form.imgUrl" placeholder="上传后自动填入" readonly style="flex:1" />
            <el-upload
              :auto-upload="false"
              :show-file-list="false"
              :on-change="handleFileChange"
              accept="image/*"
            >
              <el-button type="primary" :loading="uploading">
                {{ uploading ? '上传中...' : '选择图片' }}
              </el-button>
            </el-upload>
          </div>
          <img v-if="form.imgUrl" :src="form.imgUrl"
            style="width:200px;height:100px;object-fit:cover;border-radius:4px;margin-top:8px" />
        </el-form-item>
        <el-form-item label="跳转链接">
          <el-input v-model="form.linkUrl" placeholder="点击跳转地址" />
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
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import CommonTable from '@/components/CommonTable.vue'
import api from '@/api/cms/bannerApi.js'
import ossApi from '@/api/thirdparty/ossApi.js'

const tableRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const searchObj = reactive({ title: '', status: '' })
const uploading = ref(false)

const initForm = { title: '', imgUrl: '', fileName: '', linkUrl: '', sort: 0, status: 1 }
const form = reactive({ ...initForm })

const columns = [
  { prop: 'id', label: 'ID', width: 60 },
  { prop: 'title', label: '标题', minWidth: 120 },
  { label: '图片', width: 100, prop: 'imgUrl' },
  { prop: 'urlText', label: 'MinIO URL', minWidth: 200 },
  { prop: 'fileName', label: '原始文件名', minWidth: 140 },
  { prop: 'sort', label: '排序', width: 70 },
  { label: '状态', width: 90, prop: 'status' },
]

const handleSearch = () => tableRef.value?.refresh({ ...searchObj })
const resetSearch = () => { searchObj.title = ''; searchObj.status = ''; handleSearch() }

const add = () => {
  isEdit.value = false; editId.value = null
  dialogTitle.value = '新建轮播图'
  Object.assign(form, initForm)
  dialogVisible.value = true
}

const edit = async (id) => {
  isEdit.value = true; editId.value = id
  dialogTitle.value = '编辑轮播图'
  const res = await api.getById(id)
  if (res.data) Object.assign(form, res.data)
  dialogVisible.value = true
}

const handleFileChange = async (file) => {
  uploading.value = true
  try {
    //  1. 获取 MinIO presigned PUT URL
    const policyRes = await ossApi.getPolicy(file.name)
    const { url, accessUrl } = policyRes.data

    //  2. PUT 直传 MinIO
    await fetch(url, {
      method: 'PUT',
      body: file.raw,
      headers: { 'Content-Type': file.raw.type || 'application/octet-stream' }
    })

    //  3. 填入 URL 和原始文件名
    form.imgUrl = accessUrl
    form.fileName = file.name
    ElMessage.success('上传成功')
  } catch (e) {
    ElMessage.error('上传失败: ' + (e.message || '网络错误'))
  } finally {
    uploading.value = false
  }
}

const submitForm = async () => {
  if (!form.title) { ElMessage.warning('请输入标题'); return }
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
  ElMessageBox.confirm(`确定删除轮播图【${row.title}】？`, '提示', {
    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',
  }).then(async () => {
    await api.deleteById(row.id)
    ElMessage.success('删除成功')
    tableRef.value?.refresh()
  }).catch(() => {})
}

const handleStatusChange = async (row) => {
  row.status = row.status === 1 ? 0 : 1
  await api.updateById(row.id, { status: row.status })
  ElMessage.success(row.status === 1 ? '已启用' : '已禁用')
}
</script>

<style lang="scss" scoped>
.cms-page { display: flex; flex-direction: column; height: calc(100vh - 90px); padding: 12px 0 0 0; }
</style>
