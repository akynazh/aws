import time
import json
import random

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1


def gen() -> str:
    t = int(time.time() * 1000)
    data_ok_1 = {
        "image": "",
        "produceId": "",
        "produceName": random.choice(["菠萝", "苹果", "西瓜"]),
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(10, 50)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    data_ok_2 = {
        "image": "",
        "produceId": random.choice([0, 17, 21]),
        "produceName": "",
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(10, 50)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    data_err = {
        "image": random.choice(
            [
                "minio-edge/aws/apple.png",
                "minio-edge/aws/watermelon.png",
                "minio-edge/aws/pineapple.jpeg",
            ]
        ),
        "produceId": "",
        "produceName": "",
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(10, 50)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": t,
    }
    return json.dumps(
        random.choice([data_err, data_ok_1, data_ok_2]),
        separators=(",", ": "),
        indent=4,
        ensure_ascii=False,
    )
