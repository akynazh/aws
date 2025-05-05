# pip install pymeter
from pymeter.api.config import TestPlan, ThreadGroupWithRampUpAndHold
from pymeter.api.samplers import HttpSampler
from pymeter.api.reporters import HtmlReporter
from pymeter.api import ContentType
import random
import time
import json


def create_payload():
    return {
        "username": "67437ed1-c3b6-4995-948f-1e0a8bf5cc89",
        "password": "123456",
        "image": "",
        "produceId": "",
        "produceName": random.choice(["菠萝", "苹果", "西瓜"]),
        "employeeId": random.choice([3, 7, 8, 18, 19, 20, 21]),
        "scaleId": random.choice([2, 3, 4, 5]),
        "dataValue": float(random.randint(10, 50)),
        "dataErrorMargin": 0.1,
        "unit": 2,
        "dataTime": int(time.time() * 1000),
    }


def run_load_test(thread_count):

    sampler = HttpSampler(
        name=f"称重数据提交接口_{thread_count}并发",
        url="http://localhost:9090/weigh/record",
    ).post(body=json.dumps(create_payload()), content_type=ContentType.APPLICATION_JSON)

    thread_group = ThreadGroupWithRampUpAndHold(
        thread_count,
        30,  # ramp-up time
        35,  # hold time
        sampler,
        name=f"称重数据提交接口压力测试 - 并发数 ({thread_count})",
    )

    html_reporter = HtmlReporter()
    test_plan = TestPlan(thread_group, html_reporter)

    results = test_plan.run()
    return results


if __name__ == "__main__":
    concurrency_levels = [100, 200, 500]
    for concurrency in concurrency_levels:
        print(f"\n开始执行 {concurrency} 并发用户的测试...")
        results = run_load_test(concurrency)

        print(
            f"\n{concurrency} 并发测试结果:",
            f"总执行时长: {results.duration_milliseconds}ms",
            f"平均响应时间: {results.sample_time_mean_milliseconds}ms",
            f"最小响应时间: {results.sample_time_min_milliseconds}ms",
            f"响应时间中位数: {results.sample_time_median_milliseconds}ms",
            f"90%响应时间: {results.sample_time_90_percentile_milliseconds}ms",
            f"95%响应时间: {results.sample_time_95_percentile_milliseconds}ms",
            f"99%响应时间: {results.sample_time_99_percentile_milliseconds}ms",
            f"最大响应时间: {results.sample_time_max_milliseconds}ms",
            sep="\n\t",
        )
