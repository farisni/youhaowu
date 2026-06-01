<template>
  <div class="login-container">
    <!-- 登录区域 -->
    <div class="login-wrap">
      <div class="login">
        <div class="login-form">
          <ul class="tab">
            <li>
              <a href="javascript:;" :class="{ current: activeTab === 'scan' }" @click="activeTab = 'scan'">扫描登录</a>
            </li>
            <li>
              <a href="javascript:;" :class="{ current: activeTab === 'account' }" @click="activeTab = 'account'">账户登录</a>
            </li>
          </ul>

          <div class="content">
            <el-form
              ref="formRef"
              :model="form"
              :rules="rules"
              @submit.prevent="doLogin"
            >
              <div class="input-group">
                <span class="input-icon"><el-icon><User /></el-icon></span>
                <el-input
                  v-model="form.phone"
                  placeholder="手机号"
                  class="login-input"
                  maxlength="11"
                />
              </div>
              <div class="error-msg">{{ phoneError }}</div>

              <div class="input-group">
                <span class="input-icon"><el-icon><Lock /></el-icon></span>
                <el-input
                  v-model="form.password"
                  type="password"
                  placeholder="请输入密码"
                  class="login-input"
                  show-password
                  autocomplete
                />
              </div>
              <div class="error-msg">{{ pwdError }}</div>

              <div class="setting">
                <label class="checkbox-inline">
                  <input v-model="isKeepSecret" type="checkbox" />
                  自动登录
                </label>
                <span class="forget">忘记密码？</span>
              </div>

              <button class="btn" :disabled="loading" @click.prevent="doLogin">
                {{ loading ? '登录中...' : '登\u00a0\u00a0录' }}
              </button>
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
    </div>

    <!-- 底部版权 -->
    <div class="copyright">
      <ul>
        <li>关于我们</li>
        <li>联系我们</li>
        <li>联系客服</li>
        <li>商家入驻</li>
        <li>营销中心</li>
        <li>手机有好物</li>
        <li>销售联盟</li>
        <li>有好物社区</li>
      </ul>
      <div class="beian">© 2026 有好物 youhaowu.com 版权所有</div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus'
import authApi from '@/api/auth.js'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loading = ref(false)
const formRef = ref(null)
const activeTab = ref('account')
const isKeepSecret = ref(false)

const form = reactive({
  phone: '',
  password: '',
})

//  表单验证 rules
const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '手机号格式不正确', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' },
  ],
}

const phoneError = computed(() => '')
const pwdError = computed(() => '')

//  执行登录
const doLogin = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
  } catch {
    return
  }

  try {
    loading.value = true
    const { phone, password } = form
    const res = await authApi.login({ username: phone, password })
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.userInfo)

    if (isKeepSecret.value) {
      sessionStorage.setItem('AUTOLOGIN', JSON.stringify({ phone, password }))
    } else {
      sessionStorage.removeItem('AUTOLOGIN')
    }

    ElMessage.success('登录成功')
    const redirect = route.query.redirect || '/'
    router.push(redirect)
  } catch (err) {
    if (err?.message) ElMessage.error(err.message)
  } finally {
    loading.value = false
  }
}

//  自动登录
const autoLogin = async () => {
  const cached = sessionStorage.getItem('AUTOLOGIN')
  if (!cached) return
  const { phone, password } = JSON.parse(cached)
  try {
    const res = await authApi.login({ username: phone, password })
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.userInfo)
    ElMessage.success('自动登录成功')
    const redirect = route.query.redirect || '/'
    router.replace(redirect)
  } catch {
    sessionStorage.removeItem('AUTOLOGIN')
  }
}

onMounted(() => {
  autoLogin()
})
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;

  .login-wrap {
    flex: 1;
    display: flex;
    align-items: center;
    background-color: #e93854;

    .login {
      background: url("@/assets/images/loginbg.png") no-repeat;
      width: 1200px;
      height: 487px;
      margin: 0 auto;
      position: relative;
    }

    .login-form {
      width: 420px;
      height: 406px;
      box-sizing: border-box;
      background: #fff;
      position: absolute;
      right: 0;
      top: 45px;
      padding: 20px;

      .tab {
        display: flex;

        li {
          flex: 1;
          text-align: center;

          a {
            display: block;
            height: 50px;
            line-height: 50px;
            font-size: 20px;
            font-weight: 700;
            color: #333;
            border: 1px solid #ddd;
            box-sizing: border-box;
            text-decoration: none;
            cursor: pointer;

            &.current {
              border-bottom: none;
              border-top-color: #28a3ef;
              color: var(--color-primary);
            }
          }
        }
      }

      .content {
        width: 380px;
        height: 316px;
        box-sizing: border-box;
        border: 1px solid #ddd;
        border-top: none;
        padding: 18px;

        .input-group {
          display: flex;
          align-items: stretch;

          .input-icon {
            width: 37px;
            height: 32px;
            border: 1px solid #ccc;
            border-right: none;
            box-sizing: border-box;
            border-radius: 2px 0 0 2px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--color-text-light);
            font-size: 16px;
            flex-shrink: 0;
          }

          .login-input {
            flex: 1;

            :deep(.el-input__wrapper) {
              border-radius: 0 2px 2px 0;
              border: 1px solid #ccc;
              border-left: none;
              box-shadow: none;
              height: 32px;
            }
          }
        }

        .error-msg {
          color: var(--color-primary);
          font-size: 12px;
          height: 20px;
          line-height: 20px;
          margin-left: 37px;
          margin-bottom: 4px;
        }

        .setting {
          display: flex;
          justify-content: space-between;
          align-items: center;
          font-size: 12px;
          color: var(--color-text);
          margin-top: 4px;

          .checkbox-inline {
            display: flex;
            align-items: center;
            gap: 4px;
            cursor: pointer;

            input { vertical-align: middle; }
          }

          .forget {
            cursor: pointer;
            &:hover { color: var(--color-primary); }
          }
        }

        .btn {
          background-color: var(--color-primary);
          padding: 6px;
          font-size: 16px;
          border: 1px solid var(--color-primary);
          color: #fff;
          width: 100%;
          height: 36px;
          margin-top: 20px;
          cursor: pointer;
          outline: none;

          &:disabled {
            opacity: 0.7;
            cursor: not-allowed;
          }

          &:hover:not(:disabled) {
            background-color: var(--color-primary-dark);
          }
        }

        .call {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-top: 24px;

          ul {
            display: flex;
            gap: 8px;

            li img {
              width: 28px;
              height: 28px;
              cursor: pointer;
            }
          }

          .register {
            font-size: 14px;
            color: var(--color-text);
            text-decoration: none;

            &:hover {
              color: var(--color-primary);
              text-decoration: underline;
            }
          }
        }
      }
    }
  }

  .copyright {
    margin-top: auto;
    margin-left: auto;
    margin-right: auto;
    width: 1200px;
    text-align: center;
    line-height: 24px;

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
