import time
import json
import random
import paho.mqtt.client as mqtt

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1

mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
mqttc.username_pw_set("7686889b-1981-42cd-80bf-ae3338922c4c", "123456")
mqttc.connect(host="127.0.0.1", port=1883)
mqttc.loop_start()
while True:
    t = int(time.time() * 1000)
    data = {
        "workId": 9,
        "employeeId": random.randint(1, 10),
        "scaleId": random.randint(1, 3),
        "dataValue": float(random.randint(10, 100)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t - m1,
    }
    payload = json.dumps(data)
    msg_info = mqttc.publish("t/scale", payload, qos=2)
    msg_info.wait_for_publish()
    print(payload)
    time.sleep(5)
