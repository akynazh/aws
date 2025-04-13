# source

## START

```sh
# ALL IN ONE
docker-compose up -d
# cd aws-edge && mvn package -DskipTests && cd .. && docker-compose restart aws-edge
# cd aws-server && mvn package -DskipTests && cd .. && docker-compose restart aws-server
# docker-compose restart aws-img
# cd aws-web && pnpm run build && cd .. && docker-compose restart aws-web
# docker-compose stop
# docker-compose stop aws-server

# DATA ONLY
docker-compose up -d mysql-server mysql-edge redis emqx1 emqx2 emqx3 minio-edge minio-remote minio-bucket-init
# cd aws-edge && mvn package -DskipTests && java -jar target/aws-edge-1.0.0.jar
# cd aws-server && mvn package -DskipTests && java -jar target/aws-server-1.0.0.jar
# cd aws-img && pythonv api.py
# cd aws-web && pnpm run build && serve dist -p 80

# NOTE
# 1: docker-compose app 配置变更 > docker-compose up -d app
# 2: docker-compose app 镜像变更 > docker-compose up -d app --build
# 3: app 内容变更(jar, dist) > docker-compose restart app
# 4: docker-compose down 将会删除 container，建议使用 docker-compose stop

# SERVER
rclone copy -v $code/aws/source s2:/root/Codes/aws/ \
--exclude ".git/**" \
--exclude ".venv/**" \
--exclude "node_modules/**" \
--exclude "mysql-server/data/**" \
--exclude "mysql-edge/data/**" \
--exclude "redis/data/**" \
--exclude "emqx1/**" \
--exclude "emqx2/**" \
--exclude "emqx3/**"
# mysqldump -h 127.0.0.1 -P 3306 -uroot -p658766@Jzh --all-databases > mysql-server.sql
# chmod -R 777 emqx1 emqx2 emqx3
# docker exec emqx1 emqx ctl data export
# docker exec emqx1 emqx ctl data import xxx
```

## MODULE

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
- [x] 前台优化并添加处理称重数据的功能
- [x] 模拟器结果输出优化
- [x] 前台打包部署问题
- [x] 模拟器引入图片上传和识别功能

## DOC

1. EMQX 官方文档：https://docs.emqx.com/zh/emqx/v5.8/
2. MQTTX CLI 文档：https://mqttx.app/zh/docs/cli
3. 文章：[EMQX 5.0 全新网关框架：轻松实现多物联网协议接入](https://www.emqx.com/zh/blog/emqx-connects-multiple-iot-protocols)
4. 文章：[主流物联网协议选择：MQTT、CoAP 还是 LwM2M？](https://www.emqx.com/zh/blog/iot-protocols-mqtt-coap-lwm2m)


## NOTE

### MySQL 主从复制（GTID 模式）

#### 主库配置

MySQL 配置主库：

```ini
# my.cnf
[mysqld]
server-id = 1
gtid_mode = ON
enforce-gtid-consistency = TRUE
log-bin = mysql-bin
binlog_format = ROW
```

重启服务，然后在主库中创建用于复制的专用用户，并授予复制权限：

```sql
CREATE USER 'replica'@'%' IDENTIFIED with mysql_native_password BY 'replica';
GRANT REPLICATION SLAVE ON *.* TO 'replica'@'%';
FLUSH PRIVILEGES;

-- SELECT User, Host, plugin FROM mysql.user;
-- DROP user 'replica'@'%';
```

主库同步数据到从库：

```sql
mysqldump aws -h 127.0.0.1 -P 3306 -uroot -p658766@Jzh --add-drop-table | mysql -h 127.0.0.1 -P 3307 aws -uroot -p658766@Jzh
```

#### 从库配置

```ini
# my.cnf
[mysqld]
server-id = 2
gtid_mode = ON
enforce-gtid-consistency = TRUE
log-bin = mysql-bin
read-only = 1            
super-read-only = 1
```

重启服务，然后在从库中设置复制源：

```sql
STOP SLAVE;

CHANGE MASTER TO 
    SOURCE_HOST = 'mysql-server', -- 不可用 127.0.0.1
    SOURCE_PORT = 3306,
    SOURCE_USER = 'replica',
    SOURCE_PASSWORD = 'replica',
    MASTER_AUTO_POSITION = 1,
    MASTER_CONNECT_RETRY = 3; -- 每 3s 重试一次连接

START SLAVE;

-- 验证复制状态
SHOW SLAVE STATUS\G;
```

对于主从复制的重启恢复，如果使用 `docker-compose restart` 重启服务，主从复制会自动开启，如果使用 `docker-compose down; docker-compose up -d` 来操作，则需要在从库手动执行:

```sh
mysql -u root -P 3307 -h mysql-edge -uroot -p658766@Jzh
> reset slave & start slave
```

使用初始化脚本来自动化这一过程：

```sql
-- mysql-edge/init/slave.sql
RESET SLAVE;
START SLAVE;
```

配置 Docker，每次启动容器，都自动执行初始化脚本：

```yml
command: --init-file=/docker-entrypoint-initdb.d/slave.sql
volumes:
    - ./.dep/mysql-edge/init:/docker-entrypoint-initdb.d
```

<!-- ## [废弃] MySQL 主从复制（传统模式）

### 主库配置

MySQL 配置主库：

```ini
# my.cnf
[mysqld]
# 数据库唯一编号，主从的标识号绝对不能重复。
server-id=1
# 开启 bin-log，并指定文件目录和文件名前缀
log-bin=mysql-bin
# 同步 aws 数据库。如果同时同步多个库，就以此格式另写几行即可。如果不指定某个库同步，删除此行，表示同步所有库（除了 ignore 忽略的库）
binlog-do-db=aws
# 不同步 mysql 系统数据库。如果是多个不同步库，就以此格式另写几行；也可以在一行，中间逗号隔开。
binlog-ignore-db=mysql
# 确保 binlog 日志写入后与硬盘同步
sync_binlog=1
# bin-log 日志文件格式
binlog_format=ROW
```

重启服务，然后在主库中创建用于复制的专用用户，并授予复制权限：

```sql
CREATE USER 'replica'@'%' IDENTIFIED with mysql_native_password BY 'replica';
GRANT REPLICATION SLAVE ON *.* TO 'replica'@'%';
FLUSH PRIVILEGES;

-- SELECT User, Host, plugin FROM mysql.user;
-- DROP user 'replica'@'%';
```

主库同步数据到从库：

```sql
mysqldump aws -h 127.0.0.1 -P 3306 -uroot -p658766@Jzh --add-drop-table | mysql -h 127.0.0.1 -P 3307 aws -uroot -p658766@Jzh
```

### 从库配置

```ini
[mysqld]
# 设置从服务器 id，必须于主服务器不同
server-id=2
# 开启 binlog
log-bin=mysql-bin  
# 只读模式，防止误写
read-only = 1            
# ROOT 只读模式，防止误写      
super-read-only = 1     
```

重启服务，在主库中查看当前 binlog 文件名和位置：

```sql
mysql> SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000008 |      157 | aws          | mysql            |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

记下 File(mysql-bin.000008) 和 Position(157) 值，然后在从库中设置复制源：

```sql
STOP SLAVE;

CHANGE MASTER TO 
    SOURCE_HOST = 'mysql-server', -- 不可用 127.0.0.1
    SOURCE_PORT = 3306,
    SOURCE_USER = 'replica',
    SOURCE_PASSWORD = 'replica',
    SOURCE_LOG_FILE = 'mysql-bin.000008',
    SOURCE_LOG_POS = 157;

START SLAVE;

-- 验证复制状态
SHOW SLAVE STATUS\G;
``` -->

### MinIO 配置

```sh
# INIT
brew install minio/stable/mc
mc alias set minio-edge http://minio-edge:9100 minio-edge minio-edge
mc alias set minio-remote http://minio-remote:9000 minio-remote minio-remote
mc mb minio-edge/aws && mc anonymous set download minio-edge/aws
mc mb minio-remote/aws && mc anonymous set download minio-remote/aws

# TEST
mc cp '/Users/zh/Library/Mobile Documents/com~apple~CloudDocs/Pictures/Information/DOG.jpg' minio-edge/aws
mc cp minio-edge/aws/DOG.jpg minio-remote/aws/DOG.jpg
mc rm minio-remote/aws/DOG.jpg
mc cp './aws-img/sample/apple.png' minio-edge/aws
mc cp './aws-img/sample/watermelon.png' minio-edge/aws
# minio-edge/aws/apple.png
# minio-edge/aws/watermelon.png
```
