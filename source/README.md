# source

## START

```sh
cd aws-data && docker-compose up -d
cd aws-server && mvn package -DskipTests && java -jar target/aws-server-1.0.0.jar
cd aws-edge && mvn package -DskipTests && java -jar target/aws-edge-1.0.0.jar
```

## MODULE

- aws-data: 数据模块
- aws-edge: 边端服务
- aws-img: 果蔬识别服务
- aws-server: 后台服务
- aws-simulator: 电子秤模拟器
- aws-test: 测试模块
- aws-web: 前台服务

## TODO

- [x] QOS 和 ACK 问题
- [x] 电子秤模拟器初步完成
- [x] 主从复制
- [x] 接入缓存中间件
- [x] MQTT 用户角色问题
- [x] 边端网关优化
- [x] EMQX Docker Deploy
- [x] 解决 MQTT 重连卡住的问题
- [x] 果蔬图片识别自部署并替换 API
- [x] 后台管理添加手动处理称重数据的功能
- [x] 模型准确度计算
- [ ] 完善论文
- [ ] 前台优化并添加处理称重数据的功能
- [ ] 前台打包部署问题
- [ ] 模拟器结果输出优化
- [ ] 压力测试
- [ ] 功能测试
- [ ] 总结前端开发流程，书写博客

## DOC

1. EMQX 官方文档：https://docs.emqx.com/zh/emqx/v5.8/
2. MQTTX CLI 文档：https://mqttx.app/zh/docs/cli
3. 文章：[EMQX 5.0 全新网关框架：轻松实现多物联网协议接入](https://www.emqx.com/zh/blog/emqx-connects-multiple-iot-protocols)
4. 文章：[主流物联网协议选择：MQTT、CoAP 还是 LwM2M？](https://www.emqx.com/zh/blog/iot-protocols-mqtt-coap-lwm2m)
