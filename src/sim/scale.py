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
    t = int(time.time() * 1000)
    y1 = 24 * 3600 * 1000 * 365 * 1
    m1 = 24 * 3600 * 1000 * 30 * 1
    data = {
        "workId": 9,
        "employeeId": random.randint(1, 10),
        "scaleId": random.randint(1, 3),
        "dataValue": float(random.randint(10, 100)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t - m1,
    }
    data1 = {
        "workId": 10,
        "employeeId": random.randint(1, 10),
        "scaleId": random.randint(1, 3),
        "dataValue": float(random.randint(10, 100)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t - m1 - y1 * 2,
    }
    data2 = {
        "workId": 11,
        "employeeId": random.randint(1, 10),
        "scaleId": random.randint(1, 3),
        "dataValue": float(random.randint(10, 100)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t - m1 - y1,
    }
    payload = json.dumps(data)
    msg_info = mqttc.publish("weigh", payload, qos=2)
    msg_info.wait_for_publish()
    payload1 = json.dumps(data1)
    msg_info = mqttc.publish("weigh", payload1, qos=2)
    msg_info.wait_for_publish()
    payload2 = json.dumps(data2)
    msg_info = mqttc.publish("weigh", payload2, qos=2)
    msg_info.wait_for_publish()
    time.sleep(5)
    print(payload2)
