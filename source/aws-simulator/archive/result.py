import paho.mqtt.client as mqtt


def on_message(client, userdata, message):
    print(f"Received message: {message.payload.decode()}")


mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
mqttc.username_pw_set("result", "result")
mqttc.on_message = on_message
mqttc.connect(host="127.0.0.1", port=1883)
mqttc.subscribe(topic="t/result")
mqttc.loop_forever()
