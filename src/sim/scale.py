import time
import json
import random
import paho.mqtt.client as mqtt

unacked_publish = set()
mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)

mqttc.user_data_set(unacked_publish)
mqttc.connect("127.0.0.1")
mqttc.loop_start()
while True:
    data = {
        "workId": 1,
        "employeeId": 1,
        "scaleId": 1,
        "dataValue": random.random() * 100,
        "dataErrorMargin": 0.1,
        "unit": 1,
        "dataTime": int(time.time() * 1000),
    }
    payload = json.dumps(data)
    msg_info = mqttc.publish("weigh", payload, qos=2)
    msg_info.wait_for_publish()
    time.sleep(3)
    print(payload)
