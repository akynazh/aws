import time
import json
import random

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1


def gen() -> str:
    t = int(time.time() * 1000)
    data1 = {
        "image": "6",
        "produceId": "",
        "produceName": random.choice(["香蕉", "苹果", "西瓜"]),
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(1, 5)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    data2 = {
        "image": "",
        "produceId": random.choice([0, 1, 21]),
        "produceName": "",
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(1, 5)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    data3 = {
        "image": random.choice(
            [
                "minio-edge/aws/apple.png",
                "minio-edge/aws/watermelon.png",
                "minio-edge/aws/banana.png",
            ]
        ),
        "produceId": "",
        "produceName": "",
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(1, 5)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    return json.dumps(
        # random.choice([data1, data2, data3]),
        random.choice([data1]),
        separators=(",", ": "),
        indent=4,
        ensure_ascii=False,
    )
