import requests

# LOCAL
# resp = requests.post(
#     url="http://localhost:8000/predict", params={"image": "./sample/watermelon.png"}
# )
# print(resp.json())

# URL
resp = requests.post(
    url="http://localhost:8000/predict",
    params={
        "image": "",
        "image_url": "https://akynazh.site/images/pub/apple_181dfcdd-8cb9-4223-8aff-77166bc8e737.png",
    },
)
print(resp.json())
