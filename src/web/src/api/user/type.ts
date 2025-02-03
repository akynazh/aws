//登录接口需要携带参数ts类型
export interface LoginParams {
    username: string,
    password: string
}

interface dataType {
    token?: string
    message?: string
}

//登录接口返回数据类型
export interface LoginResultModel {
    code: number,
    data: dataType
}

interface userInfo {
    userId: number,
    avatar: string,
    username: string,
    password: string,
    desc: string,
    roles: string[],
    buttons: string[], 
    routes: string[],
    token: string
}

interface user{
    checkUser: userInfo
}

//定义服务器返回用户信息的数据类型
export interface UserInfoModel {
    code: number,
    data: user
}
