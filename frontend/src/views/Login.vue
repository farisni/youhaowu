<template>
  <div class="login-container">
    <div class="login-left">
      <div class="left-overlay">
        <div class="watermark">Admin</div>
        <h1>The Core of<br>Your Commerce.</h1>
        <p>LOW FEES • CENSORSHIP RESISTANT</p>
      </div>
    </div>
    <div class="login-right">
      <div class="login-card">
        <h2 class="right-title">你好！</h2>
        <p class="right-desc">欢迎使用 Wheatmall 管理后台，请输入账号密码登录系统。</p>
        <div class="login-form">
          <div class="login-switch">
            <span class="switch-item" :class="{ active: loginType === 'username' }" @click="loginType = 'username'">账号登录</span>
            <span class="switch-item" :class="{ active: loginType === 'phone' }" @click="loginType = 'phone'">短信登录</span>
            <div class="indicator" :class="loginType" />
          </div>
          <template v-if="loginType === 'username'">
            <div class="field-group">
              <label class="field-label">账号</label>
              <el-input v-model="form.email" placeholder="admin@youhaowu.com" :prefix-icon="Message">
                <template #suffix>
                  <span class="check-icon"><el-icon><Check /></el-icon></span>
                </template>
              </el-input>
            </div>
            <div class="field-group">
              <label class="field-label">密码</label>
              <el-input v-model="form.password" type="password" placeholder="········" show-password :prefix-icon="Lock" />
            </div>
          </template>
          <template v-if="loginType === 'phone'">
            <div class="field-group">
              <label class="field-label">手机号</label>
              <el-input v-model="form.phone" placeholder="请输入手机号" :prefix-icon="Phone" />
            </div>
            <div class="field-group">
              <label class="field-label">验证码</label>
              <el-input v-model="form.smsCode" placeholder="请输入验证码" :prefix-icon="Message">
                <template #suffix>
                  <el-button link type="primary" :disabled="countdown > 0" @click="getSMS" class="sms-btn">{{ countdown > 0 ? countdown + 's' : '获取验证码' }}</el-button>
                </template>
              </el-input>
            </div>
          </template>
          <div class="form-row">
            <el-checkbox v-model="rememberMe">
              <span class="remember-text">记住我</span>
            </el-checkbox>
            <a class="forgot-link" href="#">忘记密码？</a>
          </div>
          <el-button type="primary" :loading="loading" class="signin-btn" @click="doLogin">登录</el-button>
          <div class="signup-line">还没有账号？<a href="#" class="signup-link">申请</a></div>
        </div>
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
import { Check, Message, Lock, Phone } from '@element-plus/icons-vue'
import authApi from '@/api/authApi.js'

const router = useRouter()
const appStore = useAppStore()

const loginType = ref('username')
const loading = ref(false)
const rememberMe = ref(false)
const countdown = ref(0)

const form = reactive({
  email: 'admin@youhaowu.com',
  password: '',
  phone: '',
  smsCode: '',
})

const getSMS = () => {
  ElMessage.success('验证码已发送（Mock: 000000）')
  countdown.value = 60
  const timer = setInterval(() => { countdown.value--; if (countdown.value <= 0) clearInterval(timer) }, 1000)
}

const doLogin = async () => {
  try {
    loading.value = true
    const loginData = loginType.value === 'username'
      ? { username: 'admin', password: form.password || '123456' }
      : { phone: form.phone, smsCode: form.smsCode }
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
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700;800&display=swap");

.login-container { display: flex; height: 100vh; }

.login-left {
  flex: 1;
  background: url(../assets/images/bg.png) center / cover no-repeat;
  background-color: #0A1628;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  padding: 6vh 0 0 60px;
}

.left-overlay {
  color: #fff;
  font-family: "Poppins", sans-serif;

  .watermark {
    font-size: 48px;
    font-weight: 900;
    letter-spacing: 10px;
    color: #213b6a;
    text-transform: uppercase;
    margin-bottom: 12px;
    user-select: none;
    text-shadow: none;
  }
  text-shadow: 0 2px 12px rgba(0,0,0,0.5);
  h1 { font-size: 72px; font-weight: 900; margin: 0 0 18px; letter-spacing: 3px; line-height: 1.1; }
  p { font-size: 32px; font-weight: 800; margin: 0; letter-spacing: 9px; color: #5579b3; }
}

.login-right {
  flex: 1;
  background-color: #fff;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  padding-top: 10vh;
  padding-left: 50px;
}

.login-card {
  width: 520px;
  padding: 0 40px;
}

.right-title {
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  font-size: 52px;
  font-weight: 800;
  color: #1a1a2e;
  margin: 0 0 10px;
}

.right-desc {
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  font-size: 16px;
  line-height: 1.6;
  color: #aaa;
  margin: 0 0 36px;
}

.login-form {
  width: 100%;

  .login-switch {
    position: relative;
    display: flex;
    gap: 24px;
    margin-bottom: 28px;
    border-bottom: 1px solid #e8e8e8;
    padding-bottom: 10px;

    .switch-item {
      cursor: pointer;
      color: #999;
      font-size: 15px;
      font-weight: 600;
      transition: color 0.2s;
      &.active { color: #1a1a2e; }
    }

    .indicator {
      position: absolute;
      bottom: -1px;
      height: 2px;
      background: #1a1a2e;
      transition: all 0.3s ease;
      border-radius: 1px;
      &.username { left: 0; width: 60px; }
      &.phone { left: 84px; width: 60px; }
    }
  }

  .sms-btn {
    font-size: 13px;
    color: #409EFF;
  }

  .field-group {
    margin-bottom: 14px;
  }

  .field-label {
    display: block;
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    font-size: 16px;
    font-weight: 600;
    color: #aaa;
    margin-bottom: 8px;
    text-transform: uppercase;
    letter-spacing: 0.5px;
  }

  :deep(.el-input__wrapper) {
    border-radius: 8px;
    border: 1px solid #e0e0e0;
    background: #fff;
    box-shadow: none;
    padding: 0 14px;
    height: 56px;
    transition: border-color 0.2s;
    &:hover { border-color: #bbb; }
  }

  :deep(.el-input.is-focus .el-input__wrapper) {
    border-color: #213b6a;
  }

  :deep(.el-input__inner) {
    color: #333;
    font-size: 17px;
    &::placeholder { color: #ccc; }
  }

  .check-icon {
    color: #4caf50;
    font-size: 18px;
    display: flex;
    align-items: center;
  }

  .form-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }

  :deep(.el-checkbox__inner) {
    border-radius: 4px;
    border-color: #ccc;
  }

  :deep(.el-checkbox.is-checked .el-checkbox__inner) {
    background-color: #4caf50;
    border-color: #4caf50;
  }

  .remember-text {
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    color: #555;
    font-size: 16px;
  }

  .forgot-link {
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    font-size: 15px;
    color: #aaa;
    text-decoration: none;
    &:hover { color: #213b6a; }
  }

  .signup-line {
    text-align: left;
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    font-size: 15px;
    color: #aaa;
    margin-top: 20px;
  }

  .signup-link {
    color: #409EFF;
    text-decoration: none;
    font-weight: 600;
    &:hover { text-decoration: underline; }
  }

  .signin-btn {
    width: auto;
    min-width: 140px;
    height: 48px;
    border-radius: 8px;
    padding: 0 40px;
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    font-size: 16px;
    font-weight: 700;
    letter-spacing: 6px;
    background: #409EFF;
    border: none;
    color: #fff;
    box-shadow: 0 4px 14px rgba(64, 158, 255, 0.3);
    transition: background 0.2s, transform 0.1s;
    &:hover { background: #66b1ff; }
    &:active { transform: scale(0.98); }
  }
}
</style>
