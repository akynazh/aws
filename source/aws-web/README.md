# aws-web

## Init

项目初始化：

```sh
# vite 项目模版初始化: Vue 3 + TypeScript + Vite
pnpm create vite
# 安装依赖
pnpm install

# UI 相关
pnpm i element-plus
pnpm i @element-plus/icons-vue
# src 别名配置
pnpm install @types/node
# sass
pnpm install sass sas-loader
# mock
pnpm install -D vite-plugin-mock@2.9.6 mockjs
# axios
pnpm i axios
# 路由
pnpm install vue-router@4.1.6
# 仓库
pnpm i pinia
# 进度条
pnpm i nprogress
```

重要插件：

1. Vue VSCode Snippets: v3ts 快速生成组合式页面模版
2. Volar: 为 Vue 开发提供智能提示、类型检查、代码跳转、错误提示等完整的开发体验

依赖基本使用：

1. 在 vite.config.ts 中配置打包、插件、别名等行为
2. 在 src/main.ts 中引入相关依赖包名
3. 在 tsconfig.json 中进行编译相关配置

## Start

```sh
pnpm run dev
```

## Publish

```sh
pnpm run build
# npm install -g serve
serve dist -p 80
```

## AI

### models

根据 openapi 文档 API.json 中的 schema，生成 typescript 对象。

要求如下：

"""
1. 根据 schema 对象名称分为 user produce work scale record assignment 这些模块，其他未知的设为 common；
2. 放在 API.json 同一目录的 models 文件夹中，最后统一在 `index.ts` export。
"""

示例：

比如 `UserLoginVO` 属于 user 模块，放到 `user.ts` 中，然后在 `index.ts` 中 export。

警告：

注意不要包含重复的对象导致报错。

### api

根据 openapi 文档 API.json 中的 endpoints 和 typescript 对象分模块生成 api。

要求如下：

1. 按照 API.json 中的 endpoints 的第一个路径划分模块，比如 /user/xxx, 属于 user 模块, /weigh/scale/xxx 属于 weigh 模块，确保一共有四个模块 user weigh produce work;
2. 各个模块放到 API.json 同名目录下，比如 user 模块放到 user 文件夹下，代码写在 index.ts;
3. API 格式类似如下：

```ts
import request from "@/utils/request";
import type { UserVO } from "@/api/models";

export const reqGetUserInfo = () => request.get<UserVO>("/user");
```

警告：

必须包含所有 API, 不要遗漏！
