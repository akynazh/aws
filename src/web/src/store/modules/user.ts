import { defineStore } from "pinia";
import { reqLogin } from "@/api/user";
import type { UserState } from "./types";
import { SET_TOKEN, GET_TOKEN, REMOVE_TOKEN } from "@/utils/storage";
import type { RestResponse, UserLoginVo } from "@/api/models";

let userStore = defineStore("User", {
  state: (): UserState => {
    return {
      token: GET_TOKEN(),
    };
  },
  actions: {
    async login(data: UserLoginVo) {
      let result: RestResponse<String> = await reqLogin(data);
      if (result.code === 200) {
        this.token = result.data as string;
        SET_TOKEN(result.data as string);
        return Promise.resolve(result);
      } else {
        return Promise.reject(new Error(`${result.code}: ${result.msg}`));
      }
    },
    logout() {
      REMOVE_TOKEN()
    }
  },
  getters: {},
});
export default userStore;
