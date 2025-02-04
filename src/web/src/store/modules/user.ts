//创建用户相关的小仓库
import { defineStore } from "pinia";
//引入接口
import { reqLogin } from "@/api/user";
//引入数据类型
import type { LoginParams, LoginResultModel } from "@/api/user/type";
import type { UserState } from "./types";
//引入操作本地存储的工具方法
import { SET_TOKEN, GET_TOKEN } from "@/utils/storage";
//创建用户小仓库
let useUserStore = defineStore(
  //小仓库的名字
  "User",
  {
    //小仓库存储数据的地方
    state: (): UserState => {
      return {
        token: GET_TOKEN(), //用户唯一标识
      };
    },
    //处理异步和逻辑的地方
    actions: {
      //用户登录的方法
      async login(data: LoginParams) {
        //登录请求
        let result: LoginResultModel = await reqLogin(data);
        //登录请求：成功200->token
        //登录请求：失败201->message
        if (result.code === 200) {
          //登录成功
          //pinia仓库存储一下token
          //由于pinia存储数据利用的是js对象，所以未持久化
          this.token = result.data.token as string;
          //本地存储持久化token
          SET_TOKEN(result.data.token as string);
          //保证当前async函数返回值是一个成功的promise
          return "ok";
        } else {
          //登录失败
          return Promise.reject(new Error(result.data.message));
        }
      },
    },
    getters: {},
  }
);
//对外暴露获取小仓库的方法
export default useUserStore;
