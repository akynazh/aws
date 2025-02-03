//进行 axios 二次封装：使用请求与响应拦截器
import axios from 'axios'
import { ElMessage } from 'element-plus'
// 1.利用axios对象的create方法创建一个新的axios实例
let request = axios.create({
    // 配置基础路径
    baseURL: import.meta.env.VITE_APP_BASE_API,//基础路径上会携带/api
    timeout: 5000//超时设置
})
//2.请求拦截器
request.interceptors.request.use((config) => {
    //config是请求配置对象，headers是请求头对象,经常给服务器端传递token
    //返回配置对象
    return config
})

//3.响应拦截器
request.interceptors.response.use((response) => {
    //成功回调
    //简化数据：在/mock/user.ts中可以看到响应数据是code和data，响应拦截器只返回data即可
    return response.data
}, (error) => {
    //失败回调：处理http网络错误
    //定义一个变量：存储网络错误信息
    let message = '';
    //http状态码判断
    let statusCode = error.response.status;
    switch (statusCode) {
        case 401:
            message = 'token过期';
            break;
        case 403:
            message = '无权访问';
            break;
        case 404:
            message = '请求资源不存在';
            break;
        case 500:
            message = '服务器内部错误';
            break;
        default:
            message = '网络错误';
            break;
    }
    //提示错误信息
    ElMessage({
        type: 'error',
        message
    })
    //返回一个失败的promise对象
    return Promise.reject(error)
})

//4.导出axios实例 对外暴露
export default request
