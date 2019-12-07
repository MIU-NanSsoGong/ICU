import requests, json
#write your test video
upload_video = "../ML/ICU_cam.avi"

data = json.loads('{"number":"01069745974", "cmd":"20"}')
r = requests.post('http://127.0.0.1:5002/', json= data)
if r.text == "1":
    with open(upload_video, 'rb') as f:
        video_return = requests.post('http://127.0.0.1:5002/upload_video', data=f)
else:
    print("0")
