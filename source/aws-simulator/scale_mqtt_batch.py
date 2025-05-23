import paho.mqtt.client as mqtt
import data, time, random


def send(username, password, topic):
    mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
    mqttc.username_pw_set(username, password)
    mqttc.connect(host="localhost", port=1883)
    mqttc.loop_start()
    i = 0
    while i < 500:
        m = mqttc.publish(topic, data.gen(), qos=2)
        m.wait_for_publish()
        print(f"Published {i} messages")
        i += 1


if __name__ == "__main__":
    send(
        "67437ed1-c3b6-4995-948f-1e0a8bf5cc89",
        "123456",
        "t/scale",
    )
