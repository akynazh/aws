# %%
!pip3 install --upgrade torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu117
!pip3 install roboflow
!pip3 install --upgrade ultralytics

# %%
import torch
torch.cuda.is_available()

# %%
from kaggle_secrets import UserSecretsClient
user_secrets = UserSecretsClient()
ROBOFLOW_API_KEY = user_secrets.get_secret("ROBOFLOW_API")

from roboflow import Roboflow
rf = Roboflow(api_key=ROBOFLOW_API_KEY)
project = rf.workspace("zhihang").project("fruits-and-vegetables-knetf-9dd0s")
dataset = project.version(1).download("yolov11")

# %%
!cat /kaggle/working/Fruits-and-Vegetables-1/data.yaml

# %%
!yolo task=detect mode=train epochs=50 data=/kaggle/working/Fruits-and-Vegetables-1/data.yaml model=yolo11m.pt patience=30

# %%
#!yolo task=detect mode=train resume model=/kaggle/working/runs/detect/train/weights/best.pt data=/kaggle/working/Fruits-and-Vegetables-1/data.yaml epochs=100

# %%
!ls /kaggle/working/runs/detect/train

# %%
from IPython.display import display, Image

print("--------------------- batch0 ---------------------")
display(Image(filename='/kaggle/working/runs/detect/train/val_batch0_pred.jpg'))
display(Image(filename='/kaggle/working/runs/detect/train/val_batch0_labels.jpg'))
print("--------------------- batch1 ---------------------")
display(Image(filename='/kaggle/working/runs/detect/train/val_batch1_pred.jpg'))
display(Image(filename='/kaggle/working/runs/detect/train/val_batch1_labels.jpg'))
print("--------------------- batch2 ---------------------")
display(Image(filename='/kaggle/working/runs/detect/train/val_batch2_pred.jpg'))
display(Image(filename='/kaggle/working/runs/detect/train/val_batch2_labels.jpg'))

# %%
from ultralytics import YOLO
from PIL import Image
from IPython.display import display
from IPython.display import Image as DisplayImage
import requests

url = 'https://akynazh.site/images/pub/watermelon_d79dab34-ddf3-41fd-b6b6-8149bedc4670.png'
image = Image.open(requests.get(url, stream=True).raw)

model = YOLO("/kaggle/working/runs/detect/train/weights/best.pt")
results = model.predict(source=image, conf=0.2, save=True)  # save plotted images
# print(results)
display(DisplayImage(filename=results[0].save_dir + "/" + results[0].path))

# %%
from IPython.display import display, Image

display(Image(filename='/kaggle/working/runs/detect/train/results.png'))
display(Image(filename='/kaggle/working/runs/detect/train/confusion_matrix.png'))
display(Image(filename='/kaggle/working/runs/detect/train/labels.jpg'))
display(Image(filename='/kaggle/working/runs/detect/train/labels_correlogram.jpg'))
