import { createApp } from 'vue'
import App from '@/App.vue'
import ElementPlus from 'element-plus'
import gloablComponent from '@/components'
import 'element-plus/dist/index.css'
// @ts-ignore
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'virtual:svg-icons-register'
import '@/styles/index.scss'
// 测试代码：测试mock数据和接口能否使用
import axios from 'axios'
//引入路由
import router from './router';
//注册模板路由
//引入仓库
import pinia from './store';
//安装仓库pinia


const app = createApp(App)
app.use(router);
app.use(pinia);

app.use(ElementPlus, {
    locale: zhCn,
  })
app.use(gloablComponent)
app.mount('#app')
