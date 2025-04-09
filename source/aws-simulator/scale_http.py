import requests
import json


def send(username, password, payload):
    payload = json.loads(payload)
    payload["username"] = username
    payload["password"] = password
    resp = requests.post("http://127.0.0.1:9090/weigh/record", json=payload)
    if resp.status_code != 200:
        raise Exception(f"Failed to send data: {resp.text}")


if __name__ == "__main__":
    import data

    send("67437ed1-c3b6-4995-948f-1e0a8bf5cc89", "123456", data.gen())
