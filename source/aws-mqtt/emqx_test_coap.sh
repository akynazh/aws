brew install libcoap

# mqttx sub -u aws-server -P 123456 -h 127.0.0.1 -p 1883 -i sub -t t/scale -q 2

# auth: get token
coap-client -m post \
-e "" \
"coap://127.0.0.1/mqtt/connection?clientid=test&username=7686889b-1981-42cd-80bf-ae3338922c4c&password=123456"

# publish (with token)
coap-client -m post \
-e "COAP_TEST" \
"coap://127.0.0.1/ps/t/scale?clientid=test&token=3067575446&qos=2"
