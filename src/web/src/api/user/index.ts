import request from "@/utils/request";
import type { UserVO, UserUpdateVO, UserRegisterVO, UserUpdateMeVO, UserLoginVO, UserListVO } from "@/api/models";

export const reqGetUserInfo = () => request.get<any, UserVO>("/user");
export const reqUpdateUser = (data: UserUpdateVO) => request.put<any, UserVO>("/user", data);
export const reqAddUser = (data: UserRegisterVO) => request.post<any, UserVO>("/user", data);
export const reqUpdateMe = (data: UserUpdateMeVO) => request.put<any, UserVO>("/user/me", data);
export const reqLogin = (data: UserLoginVO) => request.post<any, string>("/user/login", data);
export const reqGetEmployee = (uid: string) => request.get<any, UserVO>(`/user/${uid}`);
export const reqGetUsers = (page: number = 0, size: number = 10) => request.get<any, UserListVO>("/user/list", { params: { page, size } });
