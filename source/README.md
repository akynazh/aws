# backup

```sh
cd $code/aws
loc='/Users/zh/Library/Mobile Documents/com~apple~CloudDocs/Backups/Codes/aws/'

git push origin-github master

git archive -o aws.tar HEAD
mv aws.tar $loc

mysqldump -h mysql-server -uroot -p658766@Jzh --all-databases | gzip > mysql-server.sql.gz
mv mysql-server.sql.gz $loc

t=source/aws-deploy/edge/.dep
tar -zcvf emqx.tar.gz $t/emqx1 $t/emqx2 $t/emqx3
mv emqx.tar.gz $loc
```

# Source

- aws-deploy: 部署模块
- aws-test: 测试模块
- aws-server: 后台服务
- aws-edge: 边端服务
- aws-web: 前台服务
- aws-simulator: 电子秤模拟器
- aws-img: 果蔬识别服务

# Todo

- [x] QOS 和 ACK 问题
- [x] 电子秤模拟器初步完成
- [x] 主从复制
- [x] 接入缓存中间件
- [x] MQTT 用户角色问题
- [x] 边端网关优化
- [x] EMQX Docker Deploy
- [x] 解决 MQTT 重连卡住的问题
- [x] 果蔬图片识别自部署并替换 API
- [ ] 电子秤模拟器优化
- [ ] 模型准确度计算
- [ ] 部署图
- [ ] 完善论文
- [ ] 压力测试
- [ ] 功能测试
- [ ] 总结前端开发流程，书写博客

# Doc

1. EMQX 官方文档：https://docs.emqx.com/zh/emqx/v5.8/
2. MQTTX CLI 文档：https://mqttx.app/zh/docs/cli
3. 文章：[EMQX 5.0 全新网关框架：轻松实现多物联网协议接入](https://www.emqx.com/zh/blog/emqx-connects-multiple-iot-protocols)
4. 文章：[主流物联网协议选择：MQTT、CoAP 还是 LwM2M？](https://www.emqx.com/zh/blog/iot-protocols-mqtt-coap-lwm2m)
