import request from "@/utils/request";
import type {
  UserLoginVo,
  RestResponse,
  UserRegisterVo,
  UserUpdateVo,
  UserVo,
  UserListVo,
} from "@/api/models";

export const reqGetUserInfo = () =>
  request.get<any, RestResponse<UserVo>>("/user");
export const reqUpdateUserInfo = (data: UserUpdateVo) =>
  request.put<any, RestResponse<UserVo>>("/user", data);
export const reqAddUser = (data: UserRegisterVo) =>
  request.post<any, RestResponse<UserVo>>("/user", data);
export const reqUpdateMe = (data: UserUpdateVo) =>
  request.put<any, RestResponse<UserVo>>("/user/me", data);
export const reqLogout = () =>
  request.post<any, RestResponse<String>>("/user/logout");
export const reqLogin = (data: UserLoginVo) =>
  request.post<any, RestResponse<String>>("/user/login", data);
export const reqGetUserById = (uid: number) =>
  request.get<any, RestResponse<UserVo>>(`/user/${uid}`);
export const reqGetUserList = (page: number, size: number) =>
  request.get<any, RestResponse<UserListVo>>(
    `/user/list?page=${page}&size=${size}`
  );
