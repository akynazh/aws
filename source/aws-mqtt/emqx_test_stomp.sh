pip install stomp.py 
# mqttx sub -u aws-server -P 123456 -h 127.0.0.1 -p 1883 -i sub -t t/scale -q 2

stomp -H localhost -P 61613 -U 7686889b-1981-42cd-80bf-ae3338922c4c -W 123456
> send t/scale test # qos=0