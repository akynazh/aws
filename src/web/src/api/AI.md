# models

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

# api

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