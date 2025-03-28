# aws-deploy

## DEPLOY

```sh
docker network create aws
cd server && docker-compose up -d
cd edge && docker-compose up -d
```

- mysql-server: 服务端数据库，主库
- mysql-edge：边端数据库，从库，这里在同一主机下部署，使用不同 hostname 来模拟不同主机，在桥接网络 aws 下通过 hostname 来访问主库
- redis：服务端 redis 服务

## MySQL 主从复制（GTID 模式）

### 主库配置

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

### 从库配置

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

## [废弃] MySQL 主从复制（传统模式）

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
```
