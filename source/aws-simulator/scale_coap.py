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

    while True:
        send("30ac4feb-b672-457d-b937-dad0db312855", "xyzzzxy", data.gen(), "t/scale")
        time.sleep(1)
