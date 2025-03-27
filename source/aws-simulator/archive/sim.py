import data
import scale_mqtt
import scale_coap
import scale_stomp
import paho.mqtt.client as mqtt
import sys


def on_message(client, userdata, message):
    print(f"\nReceived result: {message.payload.decode()}")


def setup_result_listener():
    mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
    mqttc.username_pw_set("result", "result")
    mqttc.on_message = on_message
    mqttc.connect(host="127.0.0.1", port=1883)
    mqttc.subscribe(topic="t/result")
    mqttc.loop_start()
    return mqttc


def main():
    # Get credentials
    username = input(
        "Enter username (default: 30ac4feb-b672-457d-b937-dad0db312855): "
    ).strip()
    if not username:
        username = "30ac4feb-b672-457d-b937-dad0db312855"

    password = input("Enter password (default: xyzzzxy): ").strip()
    if not password:
        password = "xyzzzxy"

    # Protocol selection
    while True:
        protocol = input("Select protocol (mqtt/coap/stomp): ").lower().strip()
        if protocol in ["mqtt", "coap", "stomp"]:
            break
        print("Invalid protocol, please try again")

    # Setup result listener
    result_client = setup_result_listener()

    try:
        while True:
            # Generate and send data
            payload = data.gen()
            print(f"\nGenerated data: {payload}")

            topic = "t/scale"
            print(f"Sending via {protocol.upper()}...")

            if protocol == "mqtt":
                scale_mqtt.send(username, password, payload, topic)
            elif protocol == "coap":
                scale_coap.send(username, password, payload, topic)
            else:  # stomp
                scale_stomp.send(username, password, payload, topic)

            input("\nPress Enter to send another message or Ctrl+C to exit...")

    except KeyboardInterrupt:
        print("\nShutting down...")
        result_client.loop_stop()
        result_client.disconnect()
        sys.exit(0)


if __name__ == "__main__":
    main()
