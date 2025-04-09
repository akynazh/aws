import stomp


def send(username, password, payload, topic):
    conn = stomp.Connection()
    conn.connect(username, password, wait=True)
    conn.send(body=payload, destination=topic)
    conn.disconnect()


if __name__ == "__main__":
    import data

    send("67437ed1-c3b6-4995-948f-1e0a8bf5cc89", "123456", data.gen(), "t/scale")
