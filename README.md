# aws (Agricultural Weighing System)

As modern agriculture advances toward intelligent transformation, the accurate collection of fruit weighing data has become a critical foundation for achieving efficient management and informed decision-making. Traditional weighing processes often face issues such as delayed data recording, frequent manual intervention, and poor device compatibility, which significantly hinder the efficiency and reliability of agricultural yield statistics.

To address these challenges, this thesis proposes and implements a cloud-based agricultural fruit weighing software system that integrates multi-protocol communication, image recognition, and data management. The system adopts a cloud-edge collaborative architecture: on the farm-side edge, fruit images and weight data are collected in real time, and a YOLO-based image recognition algorithm is used to identify fruit types before publishing the weighing messages. On the cloud side, these messages from electronic scales are subscribed to, followed by data processing and storage. The software supports multiple communication protocols, including MQTT, HTTP, and CoAP, ensuring compatibility with various types of weighing hardware for flexible integration and rapid deployment. A data management and visualization platform is developed using Spring Boot and Vue, offering efficient data management and insightful visual analytics.

This study includes comprehensive functional testing and performance evaluation under specific hardware conditions, demonstrating the core performance metrics of the system. The proposed solution provides an efficient, stable, and feature-rich platform for fruit weighing and data management in smart farming, supporting precision harvesting and the digital transformation of agriculture.

## Structure

- design: Software architecture design, database design, core functionality design
- result: Software deliverables, software acceptance process
- source: Source code
  - aws-edge: Edge service
  - aws-server: Cloud service
  - aws-img: Fruit Image Recognition Model
  - aws-simulator: Electronic Scale Simulator
  - aws-test: Performance testing
  - aws-web: Backend management interface
- thesis: Thesis LaTeX source code and LaTeX template

## Technology

- [plantuml](https://plantuml.com)
- [drawio](https://www.drawio.com)
- [emqx](https://www.emqx.com)
- [spring](https://spring.io)
- [mysql](https://www.mysql.com)
- [pymeter](https://github.com/eldaduzman/pymeter)
- [copilot](https://github.com/features/copilot)
- [rest-client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)
- [vue](https://vuejs.org)
- [yolo](https://github.com/ultralytics/ultralytics)
- [python](https://www.python.org)
- [redis](https://redis.io)
- [minio](https://min.io)
- [mqtt](https://mqtt.org)
- [java](https://www.java.com)
- [typescript](https://www.typescriptlang.org)
- [docker](https://www.docker.com)
- [latex](https://en.wikipedia.org/wiki/LaTeX)
- [xduts](https://github.com/note286/xduts)
