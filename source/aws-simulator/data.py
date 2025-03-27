import time
import json
import random

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1


def gen() -> str:
    t = int(time.time() * 1000)
    data = {
        # "image": "https://akynazh.site/images/pub/apples_cd1cda61-5610-4c10-92f1-5485a7d9892a.jpg", # 苹果
        "image": "https://akynazh.site/images/pub/images_2a317778-c9e2-4a6f-ada7-948a66d7777b.jpeg",  # 西瓜
        "image64": "",
        "produceId": "",
        "produceName": "苹果",
        "employeeId": random.choice([1, 2, 5, 8, 11, 12]),
        "scaleId": random.choice([2, 3, 4]),
        "dataValue": float(random.randint(10, 100)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    return json.dumps(data, separators=(",", ": "), indent=4, ensure_ascii=False)
