import stomp


def send(username, password, payload, topic):
    conn = stomp.Connection()
    conn.connect(username, password, wait=True)
    conn.send(body=payload, destination=topic)
    conn.disconnect()


if __name__ == "__main__":
    import data

    send("30ac4feb-b672-457d-b937-dad0db312855", "xyzzzxy", data.gen(), "t/scale")
