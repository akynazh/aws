import { defineStore } from "pinia";
import { reqLogin } from "@/api/user";
import type { LoginParams, LoginResultModel } from "@/api/user/type";
import type { UserState } from "./types";
import { SET_TOKEN, GET_TOKEN } from "@/utils/storage";

let useUserStore = defineStore("User", {
  state: (): UserState => {
    return {
      token: GET_TOKEN(),
    };
  },
  actions: {
    async login(data: LoginParams) {
      let result: LoginResultModel = await reqLogin(data);
      if (result.code === 200) {
        this.token = result.data as string;
        SET_TOKEN(result.data as string);
        return Promise.resolve(result);
      } else {
        return Promise.reject(new Error(result.msg));
      }
    },
  },
  getters: {},
});
export default useUserStore;
