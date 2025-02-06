<template>
  <div class="login_container">
    <el-row>
      <el-col :span="12" :xs="0"></el-col>
      <el-col :span="12">
        <el-form class="login_form" :model="loginFrom" :rules="rules" ref="loginForms">
          <h1>农业果实称重云端软件</h1>
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
          <el-form-item>
            <el-checkbox v-model="loginFrom.remember">记住密码</el-checkbox>
          </el-form-item>
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
    if (loginFrom.remember) {
      localStorage.setItem('userCredentials', JSON.stringify({
        uid: loginFrom.uid,
        password: loginFrom.password
      }));
    } else {
      localStorage.removeItem('userCredentials');
    }
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
      title: "登录失败" + (error as Error).message,
      message: (error as Error).message,
      duration: 2000
    });
  }
};

// 自定义校验规则需要的函数
const validatorUID = (rule: any, value: any, callback: any) => {
  if (/^\d{5,10}$/.test(value)) {
    callback();
  } else {
    callback(new Error("账号必须是 5-10 位的数字"));
  }
};
// 定义表单校验需要的配置对象
const rules = {
  // 规则对象属性
  uid: [
    {
      required: true,
      trigger: "change",
      validator: validatorUID,
    },
  ],
  password: [
    {
      required: true,
      min: 6,
      max: 15,
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
  background: url("@/assets/images/farm.png") no-repeat;
  background-color: aquamarine;
  background-size: cover;
}

.login_form {
  position: relative;
  width: 80%;
  max-width: 450px;
  top: 20vh;
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
    background: linear-gradient(135deg, #409EFF 0%, #36cfc9 100%);
    border: none;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(64, 158, 255, 0.3);
    }

    &:active {
      transform: translateY(0);
    }
  }
}

@media (max-width: 768px) {
  .login_form {
    width: 90%;
    padding: 30px;
    
    h1 {
      font-size: 24px;
    }
  }
}
</style>