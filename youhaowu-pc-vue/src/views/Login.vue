<template>
  <div class="login-page">
    <div class="login-wrap">
      <div class="login-form">
        <div class="tab">
          <div :class="{ current: activeTab === 'scan' }" @click="activeTab = 'scan'">扫描登录</div>
          <div :class="{ current: activeTab === 'account' }" @click="activeTab = 'account'">账户登录</div>
        </div>
        <div class="content">
          <el-form ref="formRef" :model="form">
            <div class="input-group">
              <span class="input-icon"><el-icon><User /></el-icon></span>
              <el-input v-model="form.phone" placeholder="手机号" class="login-input" />
            </div>
            <div class="input-group">
              <span class="input-icon"><el-icon><Lock /></el-icon></span>
              <el-input v-model="form.password" type="password" placeholder="请输入密码" class="login-input" show-password autocomplete />
            </div>
            <div class="setting">
              <label class="checkbox-inline">
                <input v-model="isKeepSecret" type="checkbox" />
                自动登录
              </label>
              <span class="forget">忘记密码？</span>
            </div>
            <button class="btn" @click.prevent="doLogin">登&nbsp;&nbsp;录</button>
          </el-form>
          <div class="call">
            <ul>
              <li><img src="@/assets/images/qq.png" alt="QQ" /></li>
              <li><img src="@/assets/images/sina.png" alt="微博" /></li>
              <li><img src="@/assets/images/ali.png" alt="支付宝" /></li>
              <li><img src="@/assets/images/weixin.png" alt="微信" /></li>
            </ul>
            <router-link class="register" to="/register">立即注册</router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="copyright">
      <ul>
        <li>关于我们</li><li>联系我们</li><li>联系客服</li><li>商家入驻</li>
        <li>营销中心</li><li>手机有好物</li><li>销售联盟</li><li>有好物社区</li>
      </ul>
      <div class="beian">© 2026 有好物 youhaowu.com 版权所有</div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeTab = ref('account')
const isKeepSecret = ref(false)
const form = reactive({ phone: 'faris', password: '123456' })

const doLogin = () => {
  userStore.setToken('mock-token')
  userStore.setUserInfo({ username: form.phone, avatar: '' })
  if (isKeepSecret.value) {
    sessionStorage.setItem('AUTOLOGIN', JSON.stringify({ phone: form.phone, password: form.password }))
  }
  ElMessage.success('登录成功')
  router.push(route.query.redirect || '/')
}

const autoLogin = () => {
  const cached = sessionStorage.getItem('AUTOLOGIN')
  if (!cached) return
  const { phone, password } = JSON.parse(cached)
  userStore.setToken('mock-token')
  userStore.setUserInfo({ username: phone, avatar: '' })
  ElMessage.success('自动登录成功')
  router.replace(route.query.redirect || '/')
}

onMounted(autoLogin)
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;

  .login-wrap {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #e93854 url("@/assets/images/loginbg.png") no-repeat center;
  }

  .login-form {
    width: 420px;
    height: 406px;
    background: #fff;
    position: relative;
    left: 390px;
    top: 22px;
    padding: 20px;
  }

  .tab {
    display: flex;
    div {
      flex: 1;
      text-align: center;
      height: 50px;
      line-height: 50px;
      font-size: 20px;
      font-weight: 700;
      color: #333;
      border: 1px solid #ddd;
      cursor: pointer;
      &.current {
        border-bottom: none;
        border-top-color: #28a3ef;
        color: var(--color-primary);
      }
    }
  }

  .content {
    width: 380px;
    height: 316px;
    border: 1px solid #ddd;
    border-top: none;
    padding: 18px;
  }

  .input-group {
    display: flex;
    margin-bottom: 16px;
    .input-icon {
      width: 37px;
      height: 40px;
      border: 1px solid #ccc;
      border-right: none;
      border-radius: 2px 0 0 2px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: var(--color-text-light);
      flex-shrink: 0;
    }
    .login-input {
      flex: 1;
      :deep(.el-input__wrapper) {
        border-radius: 0 2px 2px 0;
        border: 1px solid #ccc;
        border-left: none;
        box-shadow: none;
        height: 40px;
      }
    }
  }

  .setting {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
    color: var(--color-text);
    .checkbox-inline {
      display: flex;
      align-items: center;
      gap: 4px;
      cursor: pointer;
      input { vertical-align: middle; }
    }
    .forget { cursor: pointer; &:hover { color: var(--color-primary); } }
  }

  .btn {
    background: var(--color-primary);
    border: 1px solid var(--color-primary);
    color: #fff;
    width: 100%;
    height: 40px;
    margin-top: 20px;
    font-size: 16px;
    cursor: pointer;
    &:hover { background: var(--color-primary-dark); }
  }

  .call {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 24px;
    ul {
      display: flex;
      gap: 8px;
      li img { width: 28px; height: 28px; cursor: pointer; }
    }
    .register {
      font-size: 14px;
      color: var(--color-text);
      text-decoration: none;
      &:hover { color: var(--color-primary); text-decoration: underline; }
    }
  }

  .copyright {
    margin: auto 0 0;
    width: 1200px;
    text-align: center;
    line-height: 24px;
    align-self: center;
    ul {
      display: flex;
      justify-content: center;
      li {
        padding: 0 20px;
        margin: 15px 0;
        border-right: 1px solid var(--color-border);
        font-size: 12px;
        color: var(--color-text);
        cursor: pointer;
        &:last-child { border-right: none; }
        &:hover { color: var(--color-primary); }
      }
    }
    .beian {
      font-size: 12px;
      color: var(--color-text-light);
      margin-top: 4px;
    }
  }
}
</style>