import time
import json
import random
import paho.mqtt.client as mqtt

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1

mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
mqttc.username_pw_set("30ac4feb-b672-457d-b937-dad0db312855", "xyzzzxy")
mqttc.connect(host="127.0.0.1", port=1883)
mqttc.loop_start()

t = int(time.time() * 1000)
data = {
    "image": "",
    "image64": "",
    "produceId": "",
    "employeeId": random.choice([1, 2, 5, 8, 11, 12]),
    "scaleId": random.choice([2, 3, 4]),
    "dataValue": float(random.randint(10, 100)),
    "dataErrorMargin": 0.1,
    "unit": 2,
    "dataTime": t,
}
payload = json.dumps(data)
msg_info = mqttc.publish("t/scale", payload, qos=2)
msg_info.wait_for_publish()
print(payload)
