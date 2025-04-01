# aws-img

## 训练

1. 从 kaggle 获取到 fruits-360 数据集（已完成数据标注）
2. 将数据集上传到 roboflow
3. 训练模型：S1/S2

S1: 在 kaggle 训练

在 kaggle 云环境中，使用 roboflow api 转换得到 yolov8 格式的数据集，然后使用 yolov8 进行模型训练（参考：fruit-and-vegetable-detection-using-yolov8.ipynb，Kaggle 地址：[Fruit and Vegetable Detection using YOLOv8](https://www.kaggle.com/code/danielwiszowaty/fruit-and-vegetable-detection-using-yolov8/notebook)，Roboflow 地址：[fruits-and-vegetables-knetf](https://universe.roboflow.com/zzigmug/fruits-and-vegetables-knetf/browse?queryText=&pageSize=50&startingIndex=0&browseQuery=true)）

S2: 在 roboflow 训练

在 roboflow 中选择数据格式 yolov11 然后在 roboflow 进行模型训练，但是要付费才能下载模型。

S3: 在本地训练

类似 S1，修改对应参数。太卡了，放弃。

## 推理

```sh
pip install ultralytics
yolo task=detect mode=predict model=yolov8_best.pt source=./sample/carrot.png project=./out
```

## 准确率

模型准确率应该要在论文中体现。混淆矩阵重点关注下。

## API

```sh
.venv/bin/python api.py
```
