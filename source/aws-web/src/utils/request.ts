import axios from "axios";
import { ElMessage } from "element-plus";
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
    let statusCode = error.response.status;
    let message = error.response.data || `[ERROR]: ${statusCode}`;
    // console.log(message);
    // ElMessage.error(message);
    return Promise.reject(message);
  }
);

export default request;
