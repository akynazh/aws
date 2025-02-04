import request from "@/utils/request";
import type { LoginParams, LoginResultModel, UserInfoModel } from "./type";

enum API {
  LOGIN_URL = "/user/login",
  LOGOUT_URL = "/user/logout",
  USER_INFO_URL = "/user",
}

export const reqLogin = (data: LoginParams) =>
  request.post<any, LoginResultModel>(API.LOGIN_URL, data);
export const reqLogout = () => request.post<any, any>(API.LOGOUT_URL);
export const reqUserInfo = () =>
  request.get<any, UserInfoModel>(API.USER_INFO_URL);
