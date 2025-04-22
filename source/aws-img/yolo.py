import base64
from PIL import Image
import io
import requests
from ultralytics import YOLO
import os
import sys


def get_model_path():
    if hasattr(sys, "_MEIPASS"):  # 处理 PyInstaller 打包后的路径
        return os.path.join(sys._MEIPASS, "yolov8", "best.pt")
    else:
        return "yolov8/best.pt"  # 本地路径


# 加载 YOLO 模型
# model = YOLO("yolov11/best.pt")
model = YOLO(get_model_path())


def predict(image_local="", image_url="", image_base64=""):
    if not image_local and not image_url and not image_base64:
        return []

    if image_base64:
        # 解码 Base64 编码的图像
        img_data = base64.b64decode(image_base64)
        image = Image.open(io.BytesIO(img_data))

    elif image_url:
        # 从 URL 加载图片
        response = requests.get(image_url)
        image = Image.open(io.BytesIO(response.content))

    else:
        # 从本地路径加载图片
        image = Image.open(image_local)

    # 进行推理
    results = model(source=image, conf=0.75)
    # results = model(image

    # 解析推理结果
    predictions = []
    # 结果是一个列表，每个列表项代表一张图像的推理结果
    for result in results:
        # 使用 to_df() 方法获取 Pandas DataFrame
        df = result.to_df()  # 获取第一张图像的检测结果

        # 解析每个目标的标签和置信度
        for _, row in df.iterrows():
            print(row)
            name = row["name"]
            clazz = row["class"]
            confidence = row["confidence"]
            predictions.append(
                {"clazz": clazz, "name": name, "score": float(confidence)}
            )

    # 返回结果
    return {"result": predictions, "result_num": len(predictions)}


if __name__ == "__main__":
    predictions = predict(image_local="./sample/pineapple.jpeg")
    print(predictions)

    # predictions = predict(
    #     image_url="https://akynazh.site/images/pub/watermelon_d79dab34-ddf3-41fd-b6b6-8149bedc4670.png"
    # )
    # print(predictions)

    # with open("./sample/watermelon.png", "rb") as f:
    #     image_base64 = base64.b64encode(f.read()).decode("utf-8")
    # predictions = predict(image_base64=image_base64)
    # print(predictions)
