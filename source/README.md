# source

## START

```sh
# ALL IN ONE
docker-compose -f docker-compose-all.yml up -d
# cd aws-edge && mvn package -DskipTests && docker-compose restart aws-edge
# cd aws-server && mvn package -DskipTests && docker-compose restart aws-server
# docker-compose restart aws-img
# cd aws-web && pnpm run build && docker-compose restart aws-web
# docker-compose -f docker-compose-all.yml stop

# DATA ONLY
docker-compose up -f docker-compose-data.yml -d
# cd aws-edge && mvn package -DskipTests && java -jar target/aws-edge-1.0.0.jar
# cd aws-server && mvn package -DskipTests && java -jar target/aws-server-1.0.0.jar
# cd aws-img && pythonv api.py
# cd aws-web && pnpm run build && serve dist -p 80
# docker-compose -f docker-compose-data.yml stop

# NOTE
# 1: docker-compose app 配置变更 > docker-compose up -d app
# 2: docker-compose app 镜像变更 > docker-compose up -d app --build
# 3: app 内容变更(jar, dist) > docker-compose restart app
# 4: docker-compose down 将会删除 container，建议使用 docker-compose stop
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

### MinIO 数据滚动备份

```sh
brew install minio/stable/minio
minio server ./data
brew install minio/stable/mc
mc alias set myminio http://localhost:9000 minioadmin minioadmin
mc anonymous set download myminio/aws
# WEB: http://localhost:9000/aws/CAT.jpg 
```

如果有多个边端 MinIO 实例，并且每个边端的桶都需要同步到一个远端 MinIO 实例，可以通过 MinIO 镜像同步 来实现。每个边端 MinIO 都会通过 mc 客户端配置与远端 MinIO 的同步关系。可以使用 多源同步，即每个边端的 MinIO 配置为源，远端 MinIO 配置为目标，进行数据同步。

方案概述
	•	有多个 边端 MinIO 实例，分别插入数据。
	•	所有 边端 MinIO 将数据同步到同一个 远端 MinIO 实例。
	•	每个边端 MinIO 使用 mc 工具配置与远端 MinIO 的同步。
	•	每个边端可以定期清除数据，而远端 MinIO 会保留所有数据。

1. 修改 Docker Compose 配置

假设有 3 个边端 MinIO 实例和 1 个远端 MinIO 实例。你可以在 docker-compose.yml 文件中配置多个边端 MinIO 实例：

version: '3'
services:
  mc-client:
    image: minio/mc
    container_name: mc-client
    entrypoint: ["sleep", "infinity"]  # 保持容器运行，方便执行命令
    depends_on:
      - edge-minio-1
      - remote-minio
    networks:
      - default
  edge-minio-1:
    image: minio/minio
    container_name: edge-minio-1
    environment:
      - MINIO_ACCESS_KEY=edgeaccesskey1
      - MINIO_SECRET_KEY=edgesecretkey1
    volumes:
      - edge-data-1:/data
    ports:
      - "9001:9000"
    command: server /data

  edge-minio-2:
    image: minio/minio
    container_name: edge-minio-2
    environment:
      - MINIO_ACCESS_KEY=edgeaccesskey2
      - MINIO_SECRET_KEY=edgesecretkey2
    volumes:
      - edge-data-2:/data
    ports:
      - "9003:9000"
    command: server /data

  edge-minio-3:
    image: minio/minio
    container_name: edge-minio-3
    environment:
      - MINIO_ACCESS_KEY=edgeaccesskey3
      - MINIO_SECRET_KEY=edgesecretkey3
    volumes:
      - edge-data-3:/data
    ports:
      - "9004:9000"
    command: server /data

  remote-minio:
    image: minio/minio
    container_name: remote-minio
    environment:
      - MINIO_ACCESS_KEY=remoteaccesskey
      - MINIO_SECRET_KEY=remotesecretkey
    volumes:
      - remote-data:/data
    ports:
      - "9002:9000"
    command: server /data

volumes:
  edge-data-1:
  edge-data-2:
  edge-data-3:
  remote-data:

在这个配置中，我们有 3 个边端 MinIO 实例，分别监听端口 9001, 9003, 9004，以及 1 个远端 MinIO 实例监听端口 9002。

1. 配置 MinIO 客户端（mc）

你需要在一个机器上安装 MinIO 客户端 mc，然后配置与多个 MinIO 实例的连接。

```sh
# 配置边端 MinIO 1
mc alias set edge1 http://localhost:9001 edgeaccesskey1 edgesecretkey1

# 配置边端 MinIO 2
mc alias set edge2 http://localhost:9003 edgeaccesskey2 edgesecretkey2

# 配置边端 MinIO 3
mc alias set edge3 http://localhost:9004 edgeaccesskey3 edgesecretkey3

# 配置远端 MinIO
mc alias set remote http://localhost:9002 remoteaccesskey remotesecretkey
```

3. 配置镜像同步

你可以使用 mc mirror 命令为每个边端 MinIO 配置数据同步到远端 MinIO。

配置同步：边端 1 到远端

mc mirror --watch edge1/edge-bucket remote/remote-bucket

配置同步：边端 2 到远端

mc mirror --watch edge2/edge-bucket remote/remote-bucket

配置同步：边端 3 到远端

mc mirror --watch edge3/edge-bucket remote/remote-bucket

--watch 参数意味着 MinIO 会持续监控边端的桶，并将新的数据同步到远端。

4. 数据插入与清除

每个边端 MinIO 都可以插入数据并定期清除数据。你可以编写脚本定期上传数据，并在每次清除时清空边端 MinIO 中的数据。

插入数据的脚本（示例）

```sh
#!/bin/bash
# 向边端 MinIO 上传数据
mc cp /path/to/file edge1/edge-bucket
mc cp /path/to/file edge2/edge-bucket
mc cp /path/to/file edge3/edge-bucket
```

定期清除数据的脚本（示例）

每个边端 MinIO 可以通过定时任务（如 cron）定期清除数据：

```sh
#!/bin/bash
# 清除边端 MinIO 中的所有数据
mc rm --recursive --force edge1/edge-bucket
mc rm --recursive --force edge2/edge-bucket
mc rm --recursive --force edge3/edge-bucket
```

定时任务配置

你可以使用 cron 来定期执行清除脚本（例如每小时清除一次）：

```sh
# 编辑 crontab
crontab -e

# 每小时执行清除脚本
0 * * * * /path/to/cleanup.sh
```

5. 远端 MinIO 保留数据

由于远端 MinIO 是通过 mc mirror 配置同步的，它会始终保留来自所有边端 MinIO 的数据。即使某个边端 MinIO 清除数据，远端 MinIO 中的数据依然存在。

6. 监控与验证
	1.	启动 docker-compose 服务：

docker-compose up -d


	2.	插入数据到边端 MinIO 中，例如通过 mc cp 命令上传文件到 edge1/edge-bucket。
	3.	查看远端 MinIO（remote-bucket）中的数据，确保数据已经同步过去。
	4.	定期执行清除脚本，验证远端 MinIO 数据是否仍然存在。

⸻

总结
	•	配置多个边端 MinIO 实例，每个实例独立处理数据插入。
	•	使用 MinIO 客户端（mc）配置多个边端 MinIO 到远端 MinIO 的数据同步。
	•	每个边端可以定期清除数据，但远端 MinIO 会保留所有同步的数据。
	•	通过 mc mirror --watch 实现边端 MinIO 与远端 MinIO 的持续同步。

这种方式允许你有多个边端 MinIO 插入数据，并将所有数据集中保存在远端 MinIO 中。如果有其他问题或需要进一步帮助，随时可以问我！