import { defineStore } from "pinia";
import { reqLogin, reqGetUserInfo } from "@/api/user";
import type { UserState } from "./types";
import { SET_TOKEN, GET_TOKEN, REMOVE_TOKEN } from "@/utils/storage";
import type { UserLoginVO, UserVO } from "@/api/models";

export const useUserStore = defineStore("User", {
  state: (): UserState => {
    return {
      token: GET_TOKEN(),
      id: 0,
      uid: "",
      name: "",
      roles: "",
      createTime: 0,
      updateTime: 0,
      status: 0,
    };
  },
  actions: {
    async login(data: UserLoginVO) {
      let result: string = await reqLogin(data);
      this.token = result;
      SET_TOKEN(result);
    },
    logout() {
      REMOVE_TOKEN();
    },
    async getUserInfo() {
      let userInfo: UserVO = await reqGetUserInfo();
      console.log(userInfo)
      this.id = userInfo.id;
      this.uid = userInfo.uid;
      this.name = userInfo.name;
      this.roles = userInfo.roles;
      this.createTime = userInfo.createTime;
      this.updateTime = userInfo.updateTime;
      this.status = userInfo.status;
    },
  },
  getters: {},
});

export default useUserStore;
