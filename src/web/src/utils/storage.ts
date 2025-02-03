//封装本地存储的读取与存储方法
//本地存储存储TOKEN
export const SET_TOKEN = (token: string) => {
    localStorage.setItem('TOKEN', token)
}
//本地存储读取TOKEN
export const GET_TOKEN = () => {
    return localStorage.getItem("TOKEN")
}
