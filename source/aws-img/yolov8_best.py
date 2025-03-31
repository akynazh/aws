from PIL import Image
import requests
from io import BytesIO
from ultralytics import YOLO

# 加载 YOLO 模型
model = YOLO("yolov8_best.pt")


def predict(image="", image_url=""):
    if not image and not image_url:
        return []
    if not image:
        response = requests.get(image_url)
        image = Image.open(BytesIO(response.content))
    else:
        image = Image.open(image)

    # 进行推理
    results = model(image)

    # 解析推理结果
    predictions = []
    # 结果是一个列表，每个列表项代表一张图像的推理结果
    for result in results:
        # 使用 to_df() 方法获取 Pandas DataFrame
        df = result.to_df()  # 获取第一张图像的检测结果

        # 解析每个目标的标签和置信度
        for _, row in df.iterrows():
            label = row["name"]  # 类别名称
            confidence = row["confidence"]  # 置信度
            predictions.append({"label": label, "confidence": float(confidence)})

    # 返回结果
    return predictions


if __name__ == "__main__":
    # 输入图片路径
    predictions = predict("./sample/watermelon.png")
    print(predictions)
