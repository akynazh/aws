import { defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import { viteMockServe } from "vite-plugin-mock";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  let env = loadEnv(mode, process.cwd());
  console.log(env);
  return {
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: '@use "@/styles/variable.scss" as *;'
        },
      },
    },
    plugins: [
      vue(),
      viteMockServe({
        mockPath: "mock", // mock 文件夹路径
        // localEnabled: command === "serve", // 仅在开发模式启用
        localEnabled: false
      }),
    ],
    resolve: {
      alias: {
        "@": path.resolve("./src"), // 相对路径别名配置，使用 @ 代替 src
      },
    },
    server: {
      port: 8081,
      // API 代理跨域
      proxy: {
        [env.VITE_APP_BASE_API]: {
          target: env.VITE_SERVE,
          changeOrigin: true,
          // 路径重写: 由于服务器接口不含 /api,所以将 /api 替换为空
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
  }
  };
});
