import paho.mqtt.client as mqtt


def send(username, password, payload, topic):
    mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
    mqttc.username_pw_set(username, password)
    mqttc.connect(host="127.0.0.1", port=1883)
    mqttc.loop_start()
    msg_info = mqttc.publish(topic, payload, qos=2)
    msg_info.wait_for_publish()
