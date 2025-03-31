from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import yolov8_best as yolo

APP = FastAPI()
APP.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@APP.post("/predict")
def predict(image="", image_url="") -> dict:
    predictions = yolo.predict(image=image, image_url=image_url)
    return {"result": predictions}


if __name__ == "__main__":
    uvicorn.run("api:APP", host="0.0.0.0", port=8000, reload=True)
