brew install emqx
brew install emqx/mqttx/mqttx-cli

# foreground:
# emqx foreground
# background:
emqx start
# Listener tcp:default on 0.0.0.0:1883 started.
# Listener ssl:default on 0.0.0.0:8883 started.
# Listener ws:default on 0.0.0.0:8083 started.
# Listener wss:default on 0.0.0.0:8084 started.
# Listener http:dashboard on :18083 started.

# visit dashboard: admin/admin
# config auth(mysql: auth.sql)


# mqttx client example:
# mqttx sub -u aws-server -P 123456 -t t/scale -h 127.0.0.1

# mqttx pub -u 7686889b-1981-42cd-80bf-ae3338922c4c -P 123456 -t t/scale \
#     -m '{"id": "7686889b-1981-42cd-80bf-ae3338922c4c", "value": 100}' \
#     -h 127.0.0.1
