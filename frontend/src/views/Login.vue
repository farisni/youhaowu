<template>
  <div class="login-container">
    <div class="login-left" />
    <div class="login-right">
      <div class="right-title">Login</div>
      <div class="login-form">
        <div class="login-switch">
          <span class="switch-item" :class="{ active: loginType === 'username' }" @click="loginType = 'username'">用户名登录</span>
          <span class="switch-item" :class="{ active: loginType === 'phone' }" @click="loginType = 'phone'">短信登录</span>
          <div class="indicator" :class="loginType" />
        </div>

        <el-form v-if="loginType === 'username'" ref="userFormRef" :model="form" :rules="userRules">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password @keyup.enter="doLogin" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="loading" class="login-btn" @click="doLogin">登录</el-button>
          </el-form-item>
        </el-form>

        <el-form v-if="loginType === 'phone'" ref="phoneFormRef" :model="form" :rules="phoneRules">
          <el-form-item prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="Phone" />
          </el-form-item>
          <el-form-item prop="smsCode">
            <el-input v-model="form.smsCode" placeholder="请输入验证码" prefix-icon="Message">
              <template #suffix>
                <el-button link type="primary" :disabled="countdown > 0" @click="getSMS" class="sms-btn">
                  {{ countdown > 0 ? `${countdown}s后重新获取` : '获取验证码' }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="loading" class="login-btn" @click="doLogin">登录</el-button>
          </el-form-item>
        </el-form>

        <div class="tip-msg">* 温馨提示：建议使用谷歌、Microsoft Edge，版本 79.0.1072.62 及以上浏览器，360浏览器请使用极速模式</div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app.js'
import { addDynamicFLatRoutes } from '@/router/index.js'
import { ElMessage } from 'element-plus'
import authApi from '@/api/authApi.js'

const router = useRouter()
const appStore = useAppStore()

const loginType = ref('username')
const countdown = ref(0)
const loading = ref(false)
const userFormRef = ref(null)
const phoneFormRef = ref(null)

const form = reactive({
  username: 'admin',
  password: '123456',
  phone: '',
  smsCode: '',
})

const userRules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}
const phoneRules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' },
  ],
  smsCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

const getSMS = () => {
  ElMessage.success('验证码已发送（Mock: 000000）')
  countdown.value = 60
  const timer = setInterval(() => { countdown.value--; if (countdown.value <= 0) clearInterval(timer) }, 1000)
}

const doLogin = async () => {
  try {
    const formRef = loginType.value === 'username' ? userFormRef : phoneFormRef
    await formRef.value.validate()
    loading.value = true
    const loginData = loginType.value === 'username' ? { username: form.username, password: form.password } : { phone: form.phone, smsCode: form.smsCode }
    const res = await authApi.login(loginData)
    appStore.setToken(res.data.token)
    appStore.setUserInfo(res.data.userInfo)
    addDynamicFLatRoutes(res.data.userInfo.menu)
    ElMessage.success('登录成功')
    router.push('/home')
  } catch (err) {
    if (err?.message) ElMessage.error(err.message)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  height: 100vh;
}

.login-left {
  flex: 2;
  background: url("@/assets/images/login_bg.png") center / cover no-repeat;
  min-width: 0;
}

.login-right {
  flex: 1;
  min-width: 360px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.right-title {
  font-size: 32px;
  font-weight: 800;
  color: dodgerblue;
  margin-bottom: 30px;
}

.login-form {
  width: 100%;
  max-width: 400px;

  .login-switch {
    position: relative;
    width: 100%;
    padding: 10px 0;
    margin-bottom: 20px;
    border-bottom: 1px solid #e4e7ed;

    .switch-item {
      display: inline-block;
      text-align: center;
      width: 80px;
      margin: 0 5px;
      cursor: pointer;
      color: gray;
      &.active { color: black; }
    }

    .indicator {
      position: absolute;
      width: 90px;
      height: 2px;
      background: dodgerblue;
      bottom: 0;
      transition: all 0.3s ease;
      &.username { left: 0; }
      &.phone { left: 90px; }
    }
  }

  .el-form { margin-top: 20px; }
  .el-input { width: 100%; height: 40px; }
  .login-btn { width: 100%; height: 40px; border-radius: 20px; margin: 20px 0; }

  .sms-btn { padding: 0 8px; height: 24px; font-size: 12px; white-space: nowrap; }

  .tip-msg { margin-top: 30px; color: #a8abb2; font-size: 12px; }
}
</style>
