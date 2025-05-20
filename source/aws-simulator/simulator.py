# cd $code/aws/source/aws-simulator && pythonv simulator.py
from PyQt5.QtWidgets import (
    QApplication,
    QMainWindow,
    QWidget,
    QVBoxLayout,
    QHBoxLayout,
    QLabel,
    QLineEdit,
    QRadioButton,
    QPushButton,
    QTextEdit,
    QButtonGroup,
    QFormLayout,
    QTableWidget,
    QTableWidgetItem,
    QHeaderView,
    QComboBox,
)
from PyQt5.QtCore import Qt, QThread, pyqtSignal
import sys
import data
import requests
import json
import time
import scale_mqtt
import scale_coap
import scale_stomp
import scale_http
import paho.mqtt.client as mqtt

TOPIC_RESULT = "t/result"
TOPIC_SCALE = "t/scale"
BROKER_URL = "localhost:1883"
USERNAME_RESULT = "result"
PASSWORD_RESULT = "result"
USERNAME_SCALE = "67437ed1-c3b6-4995-948f-1e0a8bf5cc89"
PASSWORD_SCALE = "123456"
APP_NAME = "电子秤模拟器"
SIZE_WIDTH = 600
SIZE_HEIGHT = 400
PROTOCOLS = ["MQTT", "COAP", "STOMP", "HTTP"]


class ResultListener(QThread):
    result_received = pyqtSignal(str)

    def __init__(self):
        super().__init__()
        self.mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
        self.mqttc.username_pw_set(USERNAME_RESULT, PASSWORD_RESULT)
        self.mqttc.on_message = self.on_message
        t = BROKER_URL.split(":")
        self.mqttc.connect(host=t[0], port=int(t[1]))
        self.mqttc.subscribe(topic=TOPIC_RESULT)

    def on_message(self, client, userdata, message):
        self.result_received.emit(message.payload.decode())

    def run(self):
        self.mqttc.loop_forever()

    def stop(self):
        self.mqttc.loop_stop()
        self.mqttc.disconnect()


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle(APP_NAME)
        self.setMinimumSize(SIZE_WIDTH, SIZE_HEIGHT)

        main_widget = QWidget()
        self.setCentralWidget(main_widget)
        layout = QVBoxLayout(main_widget)

        form_widget = QWidget()
        form_layout = QHBoxLayout(form_widget)

        self.username = QLineEdit(USERNAME_SCALE)
        self.password = QLineEdit(PASSWORD_SCALE)
        self.image_input = QLineEdit()
        self.produce_id_input = QLineEdit()
        self.produce_name_input = QLineEdit()
        self.employee_id_input = QLineEdit()
        self.scale_id_input = QLineEdit()
        self.data_value_input = QLineEdit()
        self.data_error_margin_input = QLineEdit("0.1")
        self.unit_input = QLineEdit("2")

        self.protocol_select = QComboBox()
        self.protocol_select.addItems(PROTOCOLS)

        first_form = QFormLayout()
        first_form.addRow("通信协议:", self.protocol_select)
        first_form.addRow("电子秤编号:", self.scale_id_input)
        first_form.addRow("员工编号:", self.employee_id_input)
        form_layout.addLayout(first_form)

        second_form = QFormLayout()
        second_form.addRow("果实编号:", self.produce_id_input)
        second_form.addRow("果实名称:", self.produce_name_input)
        second_form.addRow("果实图片:", self.image_input)
        form_layout.addLayout(second_form)

        third_form = QFormLayout()
        third_form.addRow("重量值:", self.data_value_input)
        third_form.addRow("误差值:", self.data_error_margin_input)
        third_form.addRow("单位:", self.unit_input)
        form_layout.addLayout(third_form)

        layout.addWidget(form_widget)

        button_layout = QHBoxLayout()
        self.generate_btn = QPushButton("生成数据")
        self.generate_btn.clicked.connect(self.generate_data)
        self.predict_btn = QPushButton("识别果实")
        self.predict_btn.clicked.connect(self.predict_image)
        self.send_btn = QPushButton("提交数据")
        self.send_btn.clicked.connect(self.send_data)
        button_layout.addWidget(self.predict_btn)
        button_layout.addWidget(self.generate_btn)
        button_layout.addWidget(self.send_btn)
        layout.addLayout(button_layout)

        self.result_table = QTableWidget()
        self.result_table.setColumnCount(5)
        headers = [
            "记录编号",
            "通信协议",
            "作业编号",
            "果实编号",
            # "果实图片",
            # "员工编号",
            # "电子秤编号",
            # "重量值",
            # "误差值",
            # "单位",
            # "时间",
            "提交结果",
        ]
        self.result_table.setHorizontalHeaderLabels(headers)
        self.result_table.verticalHeader().setDefaultSectionSize(30)  # 设置固定行高
        self.result_table.verticalHeader().setSectionResizeMode(
            QHeaderView.Fixed
        )  # 禁止行高改变
        # 设置列宽和滚动方式
        header = self.result_table.horizontalHeader()
        for i in range(len(headers)):
            header.setSectionResizeMode(i, QHeaderView.Interactive)
            self.result_table.setColumnWidth(i, 80)  # 设置默认列宽
        self.result_table.setColumnWidth(4, 250)
        self.result_table.setWordWrap(False)  # 禁止自动换行
        self.result_table.setTextElideMode(Qt.ElideNone)  # 不显示省略号
        layout.addWidget(self.result_table)

        # Clear button
        self.clear_btn = QPushButton("清除结果")
        self.clear_btn.clicked.connect(self.clear_results)
        layout.addWidget(self.clear_btn)

        # Setup result listener
        self.result_listener = ResultListener()
        self.result_listener.result_received.connect(self.handle_result)
        self.result_listener.start()

    def generate_data(self):
        payload = data.gen()
        payload_dict = json.loads(payload)
        self.image_input.setText(payload_dict.get("image", ""))
        self.produce_id_input.setText(str(payload_dict.get("produceId", "")))
        self.produce_name_input.setText(payload_dict.get("produceName", ""))
        self.employee_id_input.setText(str(payload_dict.get("employeeId", "")))
        self.scale_id_input.setText(str(payload_dict.get("scaleId", "")))
        self.data_value_input.setText(str(payload_dict.get("dataValue", "")))
        self.data_error_margin_input.setText(
            str(payload_dict.get("dataErrorMargin", "0.1"))
        )
        self.unit_input.setText(str(payload_dict.get("unit", "2")))

    def predict_image(self):
        image = str(self.image_input.text())
        image = "http://minio-edge:9000" + image[image.find("/") :]
        resp = requests.post(
            url="http://localhost:8000/predict",
            json={"image": image, "image64": ""},
        )
        if resp.status_code == 200:
            clazz = resp.json()["result"][0]["clazz"] if resp.json()["result"] else ""
            self.produce_id_input.setText(str(clazz))

    def send_data(self):
        payload = {
            "image": self.image_input.text(),
            "produceId": self.produce_id_input.text(),
            "produceName": self.produce_name_input.text(),
            "employeeId": int(self.employee_id_input.text() or 0),
            "scaleId": int(self.scale_id_input.text() or 0),
            "dataValue": float(self.data_value_input.text() or 0),
            "dataErrorMargin": float(self.data_error_margin_input.text() or 0.1),
            "unit": int(self.unit_input.text() or 2),
            "dataTime": int(time.time() * 1000),
        }

        protocol = self.protocol_select.currentText().lower()

        try:
            if protocol == "mqtt":
                scale_mqtt.send(
                    self.username.text(),
                    self.password.text(),
                    json.dumps(payload),
                    TOPIC_SCALE,
                )
            elif protocol == "coap":
                scale_coap.send(
                    self.username.text(),
                    self.password.text(),
                    json.dumps(payload),
                    TOPIC_SCALE,
                )
            elif protocol == "stomp":
                scale_stomp.send(
                    self.username.text(),
                    self.password.text(),
                    json.dumps(payload),
                    TOPIC_SCALE,
                )
            elif protocol == "http":
                scale_http.send(
                    self.username.text(), self.password.text(), json.dumps(payload)
                )
            # 存储当前使用的协议，供结果显示使用
            self.current_protocol = protocol.upper()
        except Exception as e:
            self.add_result_row(
                payload, protocol=protocol.upper(), success=False, error_msg=str(e)
            )

    def handle_result(self, result):
        result_dict = json.loads(result)
        if result_dict["success"] == 1:
            self.add_result_row(
                result_dict["record"], protocol=self.current_protocol, success=True
            )
        else:
            self.add_result_row(
                None,
                protocol=self.current_protocol,
                success=False,
                error_msg=result_dict["reason"],
            )

    def add_result_row(self, data, protocol="", success=True, error_msg=None):
        row = self.result_table.rowCount()
        self.result_table.insertRow(row)

        def create_item(text):
            item = QTableWidgetItem(str(text))
            item.setTextAlignment(Qt.AlignLeft | Qt.AlignVCenter)
            # 设置单元格不换行
            item.setFlags(item.flags() & ~Qt.TextWordWrap)
            return item

        if success and data:
            self.result_table.setItem(row, 0, create_item(data.get("id", "")))
            self.result_table.setItem(row, 1, create_item(protocol))
            self.result_table.setItem(row, 2, create_item(data.get("workId", "")))
            self.result_table.setItem(row, 3, create_item(data.get("produceId", "")))
            # self.result_table.setItem(row, 4, create_item(data.get("image", "")))
            # self.result_table.setItem(row, 5, create_item(data.get("employeeId", "")))
            # self.result_table.setItem(row, 6, create_item(data.get("scaleId", "")))
            # self.result_table.setItem(row, 7, create_item(data.get("dataValue", "")))
            # self.result_table.setItem(
            #     row, 8, create_item(data.get("dataErrorMargin", ""))
            # )
            # self.result_table.setItem(row, 9, create_item(data.get("unit", "")))
            # self.result_table.setItem(row, 10, create_item(data.get("dataTime", "")))
            self.result_table.setItem(row, 4, create_item("成功"))
        else:
            self.result_table.setItem(row, 0, create_item("-"))
            self.result_table.setItem(row, 1, create_item(protocol))
            self.result_table.setItem(row, 2, create_item("-"))
            self.result_table.setItem(row, 3, create_item("-"))
            error_msg = (
                error_msg[error_msg.find(":") + 1 :] if error_msg else "未知错误"
            )
            error_text = f"失败: {error_msg}"
            self.result_table.setItem(row, 4, create_item(error_text))

    def clear_results(self):
        self.result_table.setRowCount(0)

    def closeEvent(self, event):
        self.result_listener.stop()
        event.accept()


def main():
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())


if __name__ == "__main__":
    main()
