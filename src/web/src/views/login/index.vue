<template>
  <div class="login_container">
    <el-row>
      <el-col :span="12" :xs="0"></el-col>
      <el-col :span="12">
        <el-form class="login_form" :model="loginFrom" :rules="rules" ref="loginForms">
          <h1>农业果实称重云端软件</h1>
          <el-form-item>
            <el-input :prefix-icon="User" v-model="loginFrom.uid"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input type="password" :prefix-icon="Lock" v-model="loginFrom.password" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" class="login_btn" type="primary" size="default" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { User, Lock } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElNotification } from "element-plus";
import userStore from "@/store/modules/user";
import { getTime } from "@/utils/time";

let store = userStore();
let router = useRouter();
let loading = ref(false);
let loginFrom = reactive({
  uid: "659811",
  password: "123456",
});
let loginForms = ref();
const login = async () => {
  await loginForms.value.validate();
  loading.value = true;
  // 通知仓库发送登录请求
  try {
    // 请求成功->进入首页展示数据
    await store.login(loginFrom);
    // 编程式导航跳转到展示数据首页
    router.push("/");
    // 登录成功的提示信息
    ElNotification({
      type: "success",
      title: `嗨,${getTime()}好`,
      message: "欢迎回来",
    });
    // 登录成功后，按钮加载效果:结束加载
    loading.value = false;
  } catch (error) {
    loading.value = false;
    ElNotification({
      type: "error",
      title: "登录失败" + (error as Error).message,
      message: (error as Error).message,
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
  width: 70%;
  top: 20vh;
  background: url("@/assets/images/farm1.jpeg") no-repeat;
  background-color: aquamarine;
  background-size: cover;
  border-radius: 15px;
  padding: 40px;

  h1 {
    color: white;
    font-size: 40px;
  }

  h2 {
    color: white;
    font-size: 20px;
    margin: 20px 0;
  }

  .login_btn {
    width: 100%;
  }
}
</style>