//统一管理项目用户相关的接口
import request from '@/utils/request'
import type { LoginParams, LoginResultModel, UserInfoModel } from './type'
//统一管理接口
enum API {
    LOGIN_URL = '/user/login',//登录接口
    USERINFO_URL = '/user/info',//获取用户信息接口
}
// 对外暴露请求函数
// 登录接口方法
export const reqLogin = (data: LoginParams) => request.post<any, LoginResultModel>(API.LOGIN_URL, data)
// 获取用户信息接口方法
export const reqUserInfo = () => request.get<any, UserInfoModel>(API.USERINFO_URL)
