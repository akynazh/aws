import requests
import json


def send(username, password, payload):
    payload = json.loads(payload)
    payload["username"] = username
    payload["password"] = password
    print(payload)
    resp = requests.post("http://127.0.0.1:9090/weigh/record", json=payload)
    if resp.status_code != 200:
        raise Exception(f"Failed to send data: {resp.text}")


if __name__ == "__main__":
    import data

    send("30ac4feb-b672-457d-b937-dad0db312855", "xyzzzxy", data.gen())
