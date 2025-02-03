<template>
  <div class="login_container">
    <el-row>
      <el-col :span="12" :xs="0"></el-col>
      <el-col :span="12">
        <el-form class="login_form" :model="loginFrom" :rules="rules" ref="loginForms">
          <h1>你好</h1>
          <h2>欢迎来到xxx系统</h2>
          <el-form-item>
            <el-input :prefix-icon="User" v-model="loginFrom.username"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              type="password"
              :prefix-icon="Lock"
              v-model="loginFrom.password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="loading"
              class="login_btn"
              type="primary"
              size="default"
              @click="login"
              >登录</el-button
            >
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
//引入用户相关的小仓库
import useUserStore from "@/store/modules/user";
//引入获取当前时间的函数
import { getTime } from "@/utils/time";
let userStore = useUserStore();
//获取路由器
let $router = useRouter();
//定义变量控制按钮加载效果
let loading = ref(false);
//收集账号与密码数据
let loginFrom = reactive({
  //默认值
  username: "admin",
  password: "111111",
});
let loginForms = ref();
//点击了登录按钮，登录按钮回调
const login = async () => {
  await loginForms.value.validate();
  //按钮加载效果:开始加载
  loading.value = true;
  // 通知仓库发送登录请求
  try {
    //请求成功->进入首页展示数据
    await userStore.login(loginFrom);
    //编程式导航跳转到展示数据首页
    $router.push("/");
    //登录成功的提示信息
    ElNotification({
      type: "success",
      title: `嗨,${getTime()}好`,
      message: "欢迎回来",
    });
    //登录成功后，按钮加载效果:结束加载
    loading.value = false;
  } catch (error) {
    //请求失败->弹出登陆失败信息
    //按钮加载效果:结束加载
    loading.value = false;
    //登录失败的提示信息
    ElNotification({
      type: "error",
      title: "登录失败",
      message: (error as Error).message,
    });
  }
};
//自定义校验规则需要的函数
const validatorUserName = (rule: any, value: any, callback: any) => {
  //rule:当前校验规则对象
  //value:当前表单项的值
  //如果符合条件callBack放行即为通过
  //如果不符合条件callBack传入错误信息即为不通过
  if (/^\d{5,10}$/.test(value)) {
    callback();
  } else {
    callback(new Error("账号必须是5-10位的数字"));
  }
};
//定义表单校验需要的配置对象
const rules = {
  //规则对象属性
  username: [
    {
      required: true, // required,代表这个字段务必要校验的
      min: 6, //min:文本长度至少多少位
      max: 10, // max:文本长度最多多少位
      message: "账号长度至少六位", // message:错误的提示信息
      trigger: "change", //trigger:触发校验表单的时机 change->文本发生变化触发校验, blur:失去焦点的时候触发校验规则
      validator: validatorUserName,
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
  background: url("@/assets/images/background.jpg") no-repeat;
  background-size: cover;
}

.login_form {
  position: relative;
  width: 80%;
  top: 30vh;
  background: url("@/assets/images/login_form.png") no-repeat;
  background-size: cover;
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
