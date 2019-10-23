import requests, json
data = json.loads('{"name":"yssong", "number":"01014741896", "cmd":"2"}')
r = requests.post('http://127.0.0.1:5000/ServerAccess', json= data)
