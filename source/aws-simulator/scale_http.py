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


"""

curl -X POST 'http://localhost:18083/api/v5/publish' \
  -H 'Content-Type: application/json' \
  -u '<appkey>:<secret>'
  -d '{
  "payload_encoding": "plain",
  "topic": "cmd/{CAR_TYPE}/{VIN}",
  "qos": 1,
  "payload": "{ \"oper\": \"unlock\" }",
  "retain": false
}'
"""
import data
from requests.auth import HTTPBasicAuth

resp = requests.post(
    "http://localhost:18083/api/v5/publish",
    auth=HTTPBasicAuth(
        "c96b32790dce0f63", "oO6Jqf6NCq9AdHyamwFNJ9AzAfZMnQ9A0D6Ne9BQTmz7tiN"
    ),
    headers={"Content-Type": "application/json"},
    json={
        "payload_encoding": "plain",
        "topic": "t/scale",
        "qos": 2,
        "payload": data.gen(),
        "retain": False,
    },
)
print(resp.text)
