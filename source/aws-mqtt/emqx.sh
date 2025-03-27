brew install emqx

emqx start
# Listener tcp:default on 0.0.0.0:1883 started.
# Listener ssl:default on 0.0.0.0:8883 started.
# Listener ws:default on 0.0.0.0:8083 started.
# Listener wss:default on 0.0.0.0:8084 started.
# Listener http:dashboard on :18083 started.

# visit dashboard: admin/admin
# config auth(mysql: 数据库设计-SQL.sql#EMQX)
# config gateway