import paho.mqtt.client as mqtt


def send(username, password, payload, topic):
    mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
    mqttc.username_pw_set(username, password)
    mqttc.connect(host="node1.emqx.io", port=1883)
    mqttc.loop_start()
    msg_info = mqttc.publish(topic, payload, qos=2)
    msg_info.wait_for_publish()


if __name__ == "__main__":
    import data, time, random

    while True:
        d = data.gen()
        print(d)
        send("67437ed1-c3b6-4995-948f-1e0a8bf5cc89", "123456", d, "t/scale")
        # send("926538b1-3a48-4fef-9ea1-20c9ce2cc36c", "123456", data.gen(), "t/scale")
        time.sleep(1)
