<template>
  <div class="login-container">
    <div class="login-card">
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
                <el-button link type="primary" :disabled="countdown > 0" @click="getSMS" class="sms-btn">{{ countdown > 0 ? countdown + 's后重新获取' : '获取验证码' }}</el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="loading" class="login-btn" @click="doLogin">登录</el-button>
          </el-form-item>
        </el-form>
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
@import url("https://fonts.googleapis.com/css2?family=Caveat:wght@700&display=swap");

.login-container {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding-right: 8vw;
  padding-right: 8vw;
  height: 100vh;
  background: url(../assets/images/login-bg3.png) center / cover no-repeat;
  background-color: #0A1628;
}

.login-card {
  width: 420px;
  padding: 50px 40px;
  background: rgba(255, 255, 255, 0.06);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.right-title {
  font-family: "Caveat", cursive;
  font-size: 64px;
  font-weight: 700;
  color: #fff;
  text-align: center;
  margin-bottom: 24px;
  text-shadow: 0 2px 12px rgba(0,0,0,0.4);
  transform: rotate(-1deg);
  letter-spacing: 2px;
}

.login-form {
  width: 100%;

  .login-switch {
    position: relative;
    width: 100%;
    padding: 10px 0;
    margin-bottom: 20px;
    border-bottom: 1px solid rgba(255,255,255,0.2);

    .switch-item {
      display: inline-block;
      text-align: center;
      width: 80px;
      margin: 0 5px;
      cursor: pointer;
      color: rgba(255,255,255,0.6);
      font-weight: 600;
      transition: color 0.2s;
      &.active { color: #fff; }
    }

    .indicator {
      position: absolute;
      width: 90px;
      height: 2px;
      background: #fff;
      bottom: -1px;
      transition: all 0.3s ease;
      border-radius: 1px;
      &.username { left: 0; }
      &.phone { left: 90px; }
    }
  }

  :deep(.el-input__wrapper) {
    border-radius: 10px;
    border: 1px solid rgba(255,255,255,0.25);
    background: rgba(255,255,255,0.1);
    box-shadow: none;
    transition: border-color 0.2s, background 0.2s;
    &:hover {
      border-color: rgba(255,255,255,0.5);
      background: rgba(255,255,255,0.15);
    }
  }

  :deep(.el-input.is-focus .el-input__wrapper) {
    border-color: rgba(255,255,255,0.6);
    background: rgba(255,255,255,0.18);
  }

  :deep(.el-input__inner) {
    color: #fff;
    &::placeholder { color: rgba(255,255,255,0.5); }
  }

  :deep(.el-input__prefix) { color: rgba(255,255,255,0.6); }

  .el-form { margin-top: 20px; }
  .el-input { width: 100%; }

  .login-btn {
    width: 100%;
    height: 45px;
    border-radius: 22px;
    margin: 20px 0;
    font-weight: 700;
    font-size: 16px;
    letter-spacing: 2px;
    background: rgba(255,255,255,0.2);
    border: 1px solid rgba(255,255,255,0.3);
    color: #fff;
    box-shadow: none;
    transition: background 0.2s;
    &:hover { background: rgba(255,255,255,0.35); }
    &:active { background: rgba(255,255,255,0.25); }
  }

  .sms-btn {
    padding: 0 8px;
    height: 24px;
    font-size: 12px;
    white-space: nowrap;
    color: rgba(255,255,255,0.7);
  }
}
</style>
