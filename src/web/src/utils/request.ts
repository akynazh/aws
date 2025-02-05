import axios from "axios";
import ElMessage from "element-plus";
import { GET_TOKEN } from "./storage";

let request = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 5000,
});
request.interceptors.request.use((config) => {
  const token = GET_TOKEN();
  if (token) {
    config.headers["Authorization"] = `Bearer ${token}`;
  }

  return config;
});
request.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    let message = error.response.message;
    let statusCode = error.response.status;
    switch (statusCode) {
      case 401:
        message = "令牌过期";
        break;
      case 403:
        message = "无权访问";
        break;
      case 404:
        message = "请求资源不存在";
        break;
      case 500:
        message = "服务器内部错误";
        break;
      default:
        message = "网络错误";
        break;
    }
    ElMessage({
      type: "error",
      message,
    });
    return Promise.reject(error);
  }
);

export default request;
