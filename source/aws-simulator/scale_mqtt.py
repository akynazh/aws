import paho.mqtt.client as mqtt


def send(username, password, payload, topic):
    mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
    mqttc.username_pw_set(username, password)
    mqttc.connect(host="127.0.0.1", port=1883)
    mqttc.loop_start()
    msg_info = mqttc.publish(topic, payload, qos=2)
    msg_info.wait_for_publish()


if __name__ == "__main__":
    import data, time

    while True:
        send("30ac4feb-b672-457d-b937-dad0db312855", "xyzzzxy", data.gen(), "t/scale")
        time.sleep(1)
