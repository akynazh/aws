import stomp


def send(username, password, payload, topic):
    conn = stomp.Connection()
    conn.connect(username, password, wait=True)
    conn.send(body=payload, destination=topic)
    conn.disconnect()
