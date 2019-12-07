import requests, json
data = json.loads('{"cmd":"21", "name":"yssong", "number":"01069745974", "ch_num":"01069745974", "stat":"100"}')
r = requests.post('http://127.0.0.1:5002/', json= data)
print(r.text)
