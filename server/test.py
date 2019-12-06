import requests, json
data = json.loads('{"name":"yssong", "number":"01069745974", "cmd":"3"}')
r = requests.post('http://127.0.0.1:5002/', json= data)
