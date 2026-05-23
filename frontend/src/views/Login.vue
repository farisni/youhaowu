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
        <h2 class="right-title">Hello!</h2>
        <p class="right-desc">Etiam pretium dapibus congue. Praesent a lorem erat. Morbi mollis posuere lacus, vel semper risus.</p>
        <div class="login-form">
          <div class="field-group">
            <label class="field-label">Email Address</label>
            <el-input v-model="form.email" placeholder="mtpiatek@gmail.com">
              <template #suffix>
                <span class="check-icon"><el-icon><Check /></el-icon></span>
              </template>
            </el-input>
          </div>
          <div class="field-group">
            <label class="field-label">Password</label>
            <el-input v-model="form.password" type="password" placeholder="········" show-password />
          </div>
          <div class="form-row">
            <el-checkbox v-model="rememberMe">
              <span class="remember-text">Remember me</span>
            </el-checkbox>
            <a class="forgot-link" href="#">Forgot password?</a>
          </div>
          <el-button type="primary" :loading="loading" class="signin-btn" @click="doLogin">Sign In</el-button>
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
import { Check } from '@element-plus/icons-vue'
import authApi from '@/api/authApi.js'

const router = useRouter()
const appStore = useAppStore()

const loading = ref(false)
const rememberMe = ref(false)

const form = reactive({
  email: 'mtpiatek@gmail.com',
  password: '',
})

const doLogin = async () => {
  try {
    loading.value = true
    const res = await authApi.login({ username: 'admin', password: form.password || '123456' })
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
  padding-left: 80px;
}

.login-card {
  width: 520px;
  padding: 0 40px;
}

.right-title {
  font-family: "Poppins", sans-serif;
  font-size: 52px;
  font-weight: 800;
  color: #1a1a2e;
  margin: 0 0 10px;
}

.right-desc {
  font-family: "Poppins", sans-serif;
  font-size: 16px;
  line-height: 1.6;
  color: #aaa;
  margin: 0 0 36px;
}

.login-form {
  width: 100%;

  .field-group {
    margin-bottom: 14px;
  }

  .field-label {
    display: block;
    font-family: "Poppins", sans-serif;
    font-size: 14px;
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
    font-size: 16px;
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
    font-family: "Poppins", sans-serif;
    color: #555;
    font-size: 15px;
  }

  .forgot-link {
    font-family: "Poppins", sans-serif;
    font-size: 14px;
    color: #aaa;
    text-decoration: none;
    &:hover { color: #213b6a; }
  }

  .signin-btn {
    width: 100%;
    height: 56px;
    border-radius: 10px;
    font-family: "Poppins", sans-serif;
    font-size: 18px;
    font-weight: 700;
    letter-spacing: 1px;
    background: #213b6a;
    border: none;
    color: #fff;
    box-shadow: 0 4px 14px rgba(33, 59, 106, 0.3);
    transition: background 0.2s, transform 0.1s;
    &:hover { background: #1a2f56; }
    &:active { transform: scale(0.98); }
  }
}
</style>