import time
import json
import random

y1 = 24 * 3600 * 1000 * 365 * 1
m1 = 24 * 3600 * 1000 * 30 * 1


def gen() -> str:
    t = int(time.time() * 1000)
    data_ok = {
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
    data_err = {
        "image": random.choice(
            [
                "https://akynazh.site/images/pub/apples_cd1cda61-5610-4c10-92f1-5485a7d9892a.jpg",
                "https://akynazh.site/images/pub/watermelon_d79dab34-ddf3-41fd-b6b6-8149bedc4670.png",
                "https://akynazh.site/images/pub/pineapple_a7afe351-9466-4355-8151-71c81b57b070.jpeg",
            ]
        ),  # 苹果
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
        random.choice([data_err, data_ok, data_ok]),
        separators=(",", ": "),
        indent=4,
        ensure_ascii=False,
    )
