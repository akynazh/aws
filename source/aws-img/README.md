# aws-img

## 训练

1. 从 kaggle 获取到 fruits-360 数据集（已完成数据标注）
2. 将数据集上传到 roboflow
3. 训练模型
   1. 在 kaggle 训练：在 kaggle 云环境中，使用 roboflow api 转换得到 yolov8 格式的数据集，然后使用 yolov8 进行模型训练（参考：fruit-and-vegetable-detection-using-yolov8.ipynb，Kaggle 地址：[Fruit and Vegetable Detection using YOLOv8](https://www.kaggle.com/code/danielwiszowaty/fruit-and-vegetable-detection-using-yolov8/notebook)）
   2. 在 roboflow 训练：在 roboflow 中选择数据格式 yolov11 然后在 roboflow 进行模型训练

## 推理

```sh
pip install ultralytics
yolo task=detect mode=predict model=yolov8_best.pt source=./sample/carrot.png project=./out
```

## 准确率

模型准确率应该要在论文中体现。

## API

FastAPI 构建。