# brew install libcoap
import os
import uuid


def send(username, password, payload, topic):
    clientid = str(uuid.uuid4())
    baseUrl = "coap://127.0.0.1:5683"
    qos = 2
    auth_url = f"{baseUrl}/mqtt/connection?clientid={clientid}&username={username}&password={password}"

    with os.popen(f"coap-client -m post -e '' '{auth_url}'") as p:
        token = p.read().strip()
        url = f"{baseUrl}/ps/{topic}?clientid={clientid}&token={token}&qos={qos}"
        with os.popen(f"coap-client -m post -e '{payload}' '{url}'") as p1:
            pass


if __name__ == "__main__":
    import data, time

    send("67437ed1-c3b6-4995-948f-1e0a8bf5cc89", "123456", data.gen(), "t/scale")
