<template>
  <div class="login h-screen flex-center" style="background: #f5f5f5">
    <el-card style="width: 400px">
      <template #header>
        <h2 style="margin: 0">登录</h2>
      </template>
      <el-form @submit.prevent="doLogin">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" style="width: 100%" @click="doLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus'
import authApi from '@/api/auth.js'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const form = reactive({
  username: 'faris',
  password: '123456',
})

const doLogin = async () => {
  try {
    loading.value = true
    const res = await authApi.login(form)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.userInfo)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (err) {
    if (err?.message) ElMessage.error(err.message)
  } finally {
    loading.value = false
  }
}
</script>
