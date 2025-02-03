// 引入项目中全部的全局组件
import SvgIcon from './SvgIcon/index.vue';
import Pagination from './Pagination/index.vue';
import type { App, Component } from 'vue';
// 全局对象
const allGlobalComponents: { [name: string]: Component } = { SvgIcon, Pagination };
export default {
    // 务必叫做 install 方法
    install(app: App) {
        // 注册项目全部的全局组件
        Object.keys(allGlobalComponents).forEach((key: string) => {
            // 注册为全局组件
            app.component(key, allGlobalComponents[key]);
        })
    }
}
