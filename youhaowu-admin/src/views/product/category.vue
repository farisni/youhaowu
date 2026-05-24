<template>
  <div class="category-container">
    <div class="operation-bar">
        <el-switch v-model="draggable" active-text="开启拖拽" inactive-text="关闭拖拽" />
        <el-button v-if="draggable" type="primary" @click="batchSave">批量保存</el-button>
        <el-button type="danger" @click="batchDelete">批量删除</el-button>
    </div>
    <el-tree
      :data="menus"
      :props="defaultProps"
      :expand-on-click-node="false"
      show-checkbox
      node-key="catId"
      :default-expanded-keys="expandedKey"
      :draggable="draggable"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
      ref="menuTreeRef"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ node.label }}</span>
          <span class="tree-actions">
            <el-button v-if="node.level <= 2" type="text" size="small" @click="append(data)">
              Append
            </el-button>
            <el-button type="text" size="small" @click="edit(data)">编辑</el-button>
            <el-button v-if="node.childNodes.length === 0" type="text" size="small" @click="remove(node, data)">
              删除
            </el-button>
          </span>
        </span>
      </template>
    </el-tree>

    <el-dialog v-model="dialogVisible" :title="title" width="30%" :close-on-click-modal="false">
      <el-form :model="category">
        <el-form-item label="分类名称">
          <el-input v-model="category.name" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" />
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input v-model="category.productUnit" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitData">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import http from '@/utils/http.js'

const draggable = ref(false)
const dialogVisible = ref(false)
const dialogType = ref('')
const title = ref('')
const menus = ref([])
const expandedKey = ref([])
const updateNodes = ref([])
const maxLevel = ref(0)
const menuTreeRef = ref(null)
const defaultProps = { children: 'children', label: 'name' }

const category = reactive({
  name: '',
  parentCid: 0,
  catLevel: 0,
  showStatus: 1,
  sort: 0,
  productUnit: '',
  icon: '',
  catId: null,
})

// 加载分类树
const getMenus = async () => {
  const res = await http.get('/product/category/list/tree')
  if (res.data) menus.value = res.data
}

// 批量删除
const batchDelete = () => {
  const checkedNodes = menuTreeRef.value?.getCheckedNodes?.() || []
  if (checkedNodes.length === 0) {
    ElMessage.warning('请先勾选要删除的分类')
    return
  }
  ElMessageBox.confirm(`是否批量删除选中的 ${checkedNodes.length} 个分类？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const ids = checkedNodes.map(n => n.catId)
    await http.post('/product/category/delete', ids)
    ElMessage.success('批量删除成功')
    getMenus()
  }).catch(() => {})
}

// 拖拽处理
const handleDrop = (draggingNode, dropNode, dropType) => {
  let pCid = 0
  let siblings = null
  if (dropType === 'before' || dropType === 'after') {
    pCid = dropNode.parent.level === 0 ? 0 : dropNode.parent.data.catId
    siblings = dropNode.parent.childNodes
  } else {
    pCid = dropNode.data.catId
    siblings = dropNode.childNodes
  }
  if (siblings) {
    siblings.forEach((child, index) => {
      const existing = updateNodes.value.find(n => n.catId === child.data.catId)
      if (existing) {
        existing.sort = index
      } else {
        updateNodes.value.push({ catId: child.data.catId, sort: index, parentCid: pCid })
      }
    })
  }
}

const batchSave = async () => {
  if (updateNodes.value.length === 0) {
    ElMessage.warning('没有需要保存的排序变更')
    return
  }
  await http.post('/product/category/update/sort', updateNodes.value)
  ElMessage.success('排序保存成功')
  updateNodes.value = []
  maxLevel.value = 0
  getMenus()
}

// 拖拽限制三级
const countNodeLevel = (node) => {
  if (node.childNodes && node.childNodes.length > 0) {
    node.childNodes.forEach(child => {
      if (child.level > maxLevel.value) maxLevel.value = child.level
      countNodeLevel(child)
    })
  }
}

const allowDrop = (draggingNode, dropNode, type) => {
  maxLevel.value = draggingNode.level
  countNodeLevel(draggingNode)
  const deep = maxLevel.value - draggingNode.level + 1
  if (type === 'inner') return deep + dropNode.level <= 3
  return deep + dropNode.parent.level <= 3
}

// 新增
const append = (data) => {
  dialogType.value = 'add'
  title.value = '添加分类'
  dialogVisible.value = true
  category.parentCid = data.catId
  category.catLevel = data.catLevel * 1 + 1
  category.catId = null
  category.name = ''
  category.icon = ''
  category.productUnit = ''
  category.sort = 0
  category.showStatus = 1
}

// 编辑：用树数据回填
const edit = (data) => {
  dialogType.value = 'edit'
  title.value = '修改分类'
  category.name = data.name
  category.catId = data.catId
  category.icon = data.icon || ''
  category.productUnit = data.productUnit || ''
  category.parentCid = data.parentCid
  category.catLevel = data.catLevel
  category.sort = data.sort
  category.showStatus = data.showStatus
  dialogVisible.value = true
}

// 单个删除
const remove = (node, data) => {
  ElMessageBox.confirm(`是否删除【${data.name}】？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    await http.post('/product/category/delete', [data.catId])
    ElMessage.success('删除成功')
    getMenus()
    expandedKey.value = [node.parent?.data?.catId]
  }).catch(() => {})
}

// 提交表单
const submitData = async () => {
  const { catId, name, icon, productUnit, parentCid, catLevel, showStatus, sort } = category
  const payload = { catId, name, icon, productUnit, parentCid, catLevel, showStatus, sort }

  if (dialogType.value === 'add') {
    await http.post('/product/category/save', payload)
    ElMessage.success('添加成功')
  } else {
    await http.post(`/product/category/update/${catId}`, payload)
    ElMessage.success('修改成功')
  }
  dialogVisible.value = false
  getMenus()
  expandedKey.value = [category.parentCid]
}

onMounted(() => {
  getMenus()
})
</script>

<style lang="scss" scoped>
.category-container {
  padding: 16px;
}

.operation-bar {
  height: 50px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 10px;
  border: 1px solid #EBEEF5;
  border-radius: 4px;
  margin-bottom: 10px;
}
.custom-tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  .tree-actions {
    display: flex;
    gap: 4px;
  }
}
</style>
