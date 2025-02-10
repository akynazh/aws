<template>
  <div class="login_container">
    <el-row class="login-row">
      <el-col :span="12" :xs="0" class="left-section">
        <img src="@/assets/images/xdu.png" alt="XDU Logo" class="xdu-logo">
      </el-col>
      <el-col :span="12" class="right-section">
        <el-form class="login_form" :model="loginFrom" :rules="rules" ref="loginForms">
          <div class="form-title">农业果实称重云端软件</div>
          <el-form-item prop="uid">
            <el-input
              :prefix-icon="User"
              v-model="loginFrom.uid"
              placeholder="请输入账号"
              clearable
              @keyup.enter="login">
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              type="password"
              :prefix-icon="Lock"
              v-model="loginFrom.password"
              placeholder="请输入密码"
              show-password
              clearable
              @keyup.enter="login">
            </el-input>
          </el-form-item>
          <!-- <el-form-item>
            <el-checkbox v-model="loginFrom.remember">记住密码</el-checkbox>
          </el-form-item> -->
          <el-form-item>
            <el-button
              :loading="loading"
              class="login_btn"
              type="primary"
              size="large"
              @click="login">
              {{ loading ? '登录中...' : '登录' }}
            </el-button>
          </el-form-item>
        </el-form>
        <div class="copyright-text">
          © 2025 西安电子科技大学 江志航
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { User, Lock } from "@element-plus/icons-vue";
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElNotification } from "element-plus";
import useUserStore from "@/store/modules/user";
import { getTime } from "@/utils/time";

let store = useUserStore();
let router = useRouter();
let loading = ref(false);
let loginFrom = reactive({
  uid: "659811",
  password: "123456",
  remember: false
});
let loginForms = ref();
const login = async () => {
  await loginForms.value.validate();
  loading.value = true;
  try {
    await store.login(loginFrom);
    // 如果记住密码，保存到本地存储
    // if (loginFrom.remember) {
    //   localStorage.setItem('userCredentials', JSON.stringify({
    //     uid: loginFrom.uid,
    //     password: loginFrom.password
    //   }));
    // } else {
    //   localStorage.removeItem('userCredentials');
    // }
    router.push("/");
    await store.getUserInfo()
    ElNotification({
      type: "success",
      title: `${getTime()}好`,
      message: `${store.name}，欢迎回来`,
      duration: 1000
    });
    loading.value = false;
  } catch (error) {
    loading.value = false;
    ElNotification({
      type: "error",
      title: "登录失败",
      message: (error as Error).message,
      duration: 2000
    });
  }
};

// 自定义校验规则需要的函数
// const validatorUID = (rule: any, value: any, callback: any) => {
//   if (/^\d{5,10}$/.test(value)) {
//     callback();
//   } else {
//     callback(new Error("账号必须是 5-10 位的数字"));
//   }
// };
// 定义表单校验需要的配置对象
const rules = {
  // 规则对象属性
  uid: [
    {
      required: true,
      // min: 6,
      // max: 15,
      message: "帐号不能为空",
      trigger: "change",
      // validator: validatorUID,
    },
  ],
  password: [
    {
      required: true,
      min: 6,
      // max: 15,
      message: "密码长度至少六位",
      trigger: "change",
    },
  ],
};

// 页面加载时检查是否有保存的登录信息
onMounted(() => {
  const savedCredentials = localStorage.getItem('userCredentials');
  if (savedCredentials) {
    const { uid, password } = JSON.parse(savedCredentials);
    loginFrom.uid = uid;
    loginFrom.password = password;
    loginFrom.remember = true;
  }
});
</script>

<style scoped lang="scss">
.login_container {
  width: 100%;
  height: 100vh;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-row {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  // animation: float 10s ease-in-out infinite;
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-15px);
  }
  100% {
    transform: translateY(0px);
  }
}

.left-section {
  height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #e6f3ff 0%, #f0f9ff 100%);
}

.right-section {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 600px;  // 与左侧section高度保持一致
  background: linear-gradient(135deg, #ededed 0%, #f0f9ff 100%);
}

.xdu-logo {
  max-width: 80%;
  max-height: 80%;
  object-fit: contain;
}

.login_form {
  position: relative;
  width: 80%;
  max-width: 450px;
  margin: 0 auto;  // 修改这里，删除 top margin
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  padding: 40px;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
  }

  h1 {
    color: #2c3e50;
    font-size: 32px;
    margin-bottom: 30px;
    text-align: center;
    font-weight: 600;
  }

  .form-title {
    font-size: 32px;
    color: #303133;
    text-align: center;
    margin-bottom: 30px;
    font-weight: bold;
  }

  .el-form-item {
    margin-bottom: 25px;
  }

  .el-input {
    --el-input-hover-border: #409EFF;
    --el-input-focus-border: #409EFF;
  }

  .login_btn {
    width: 100%;
    height: 45px;
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 1px;
    background: $base-color;
    border: none;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba($base-color, 0.3);
      background: $el-color-primary-light-3;
    }

    &:active {
      transform: translateY(0);
      background: $el-color-primary-dark-2;
    }
  }
}

.copyright-text {
  text-align: center;
  font-size: 12px;
  color: #909399;
  margin-top: 20px;
  position: absolute;
  bottom: 20px;
  width: 100%;
}

@media (max-width: 768px) {
  .login_form {
    width: 90%;
    padding: 30px;
    
    h1 {
      font-size: 24px;
    }

    .form-title {
      font-size: 28px;
    }
  }

  .system-title {
    font-size: 28px;
    top: 20px;
  }

  .copyright-text {
    font-size: 10px;
    bottom: 10px;
  }
}
</style>