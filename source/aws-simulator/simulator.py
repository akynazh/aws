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
)
from PyQt5.QtCore import Qt, QThread, pyqtSignal
from PyQt5.QtGui import QIcon
import sys
import data
import json
import scale_mqtt
import scale_coap
import scale_stomp
import scale_http
import paho.mqtt.client as mqtt
import os

TOPIC_RESULT = "t/result"
TOPIC_SCALE = "t/scale"
BROKER_URL = "node1.emqx.io:1883"
USERNAME_RESULT = "result"
PASSWORD_RESULT = "result"
USERNAME_SCALE = "30ac4feb-b672-457d-b937-dad0db312855"
PASSWORD_SCALE = "xyzzzxy"


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
        self.setWindowTitle("电子秤模拟器")
        self.setMinimumSize(800, 600)

        main_widget = QWidget()
        self.setCentralWidget(main_widget)
        layout = QVBoxLayout(main_widget)

        # Credentials
        cred_layout = QVBoxLayout()
        self.username = QLineEdit(USERNAME_SCALE)
        self.password = QLineEdit(PASSWORD_SCALE)
        cred_layout.addWidget(QLabel("电子秤编号:"))
        cred_layout.addWidget(self.username)
        cred_layout.addWidget(QLabel("电子秤密钥:"))
        cred_layout.addWidget(self.password)
        layout.addLayout(cred_layout)

        # Protocol selection
        proto_layout = QHBoxLayout()
        self.protocol_group = QButtonGroup()
        protocols = ["MQTT", "CoAP", "STOMP", "HTTP"]
        for i, proto in enumerate(protocols):
            radio = QRadioButton(proto)
            if i == 0:
                radio.setChecked(True)
            self.protocol_group.addButton(radio, i)
            proto_layout.addWidget(radio)
        layout.addLayout(proto_layout)

        # Send button
        self.send_btn = QPushButton("生成数据并提交")
        self.send_btn.clicked.connect(self.send_data)
        layout.addWidget(self.send_btn)

        # Split display area into two columns
        display_layout = QHBoxLayout()

        # Left column - Generated Payload
        left_column = QVBoxLayout()
        left_column.addWidget(QLabel("生成的数据"))
        self.payload_display = QTextEdit()
        self.payload_display.setReadOnly(True)
        left_column.addWidget(self.payload_display)
        display_layout.addLayout(left_column)

        # Right column - Results
        right_column = QVBoxLayout()
        right_column.addWidget(QLabel("提交结果"))
        self.result_display = QTextEdit()
        self.result_display.setReadOnly(True)
        right_column.addWidget(self.result_display)
        display_layout.addLayout(right_column)

        layout.addLayout(display_layout)

        # Clear buttons
        clear_layout = QHBoxLayout()
        self.clear_payload_btn = QPushButton("清除生成的数据")
        self.clear_payload_btn.clicked.connect(self.payload_display.clear)
        self.clear_result_btn = QPushButton("清除提交结果")
        self.clear_result_btn.clicked.connect(self.result_display.clear)
        clear_layout.addWidget(self.clear_payload_btn)
        clear_layout.addWidget(self.clear_result_btn)
        layout.addLayout(clear_layout)

        # Setup result listener
        self.result_listener = ResultListener()
        self.result_listener.result_received.connect(self.handle_result)
        self.result_listener.start()

    def send_data(self):
        payload = data.gen()
        self.payload_display.append(f"\n生成数据:\n{payload}\n")

        protocol_map = {0: "mqtt", 1: "coap", 2: "stomp", 3: "http"}
        protocol = protocol_map[self.protocol_group.checkedId()]

        username = self.username.text()
        password = self.password.text()
        topic = TOPIC_SCALE

        self.result_display.append(f"通过 {protocol.upper()} 协议提交数据......")

        try:
            if protocol == "mqtt":
                scale_mqtt.send(username, password, payload, topic)
            elif protocol == "coap":
                scale_coap.send(username, password, payload, topic)
            elif protocol == "stomp":
                scale_stomp.send(username, password, payload, topic)
            elif protocol == "http":
                scale_http.send(username, password, payload)
        except Exception as e:
            self.result_display.append(f"提交失败: {str(e)}")

    def handle_result(self, result):
        result = json.loads(result)
        success = result["success"]
        if success == 1:
            record = json.dumps(
                result["record"], separators=(",", ": "), indent=4, ensure_ascii=False
            )
            self.result_display.append(f"\提交成功: {record}\n")
        else:
            self.result_display.append(f"\提交失败: {result['reason']}\n")

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
