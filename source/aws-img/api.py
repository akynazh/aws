from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import yolo
from pydantic import BaseModel


class Form(BaseModel):
    image: str
    image64: str


APP = FastAPI()
APP.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@APP.post("/predict")
def predict(form: Form) -> dict:
    return yolo.predict(image_url=form.image, image_base64=form.image64)


if __name__ == "__main__":
    uvicorn.run("api:APP", host="0.0.0.0", port=8000, reload=True)
