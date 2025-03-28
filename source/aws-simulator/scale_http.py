import requests
import json


def send(username, password, payload):
    resp = requests.post(
        "http://127.0.0.1:8080/user/login",
        json={"uid": username, "password": password},
    )
    if resp.status_code != 200:
        raise Exception(f"Login failed: {resp.text}")
    token = resp.text
    resp = requests.post(
        "http://127.0.0.1:8080/weigh/record",
        json=json.loads(payload),
        headers={"Authorization": f"Bearer {token}"},
    )
    if resp.status_code != 200:
        raise Exception(f"Failed to send data: {resp.text}")
    return json.dumps(resp.json(), separators=(",", ": "), indent=4, ensure_ascii=False)
