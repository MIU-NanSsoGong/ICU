import requests
#write your test video
upload_video = "jj.avi"

with open(upload_video, 'rb') as f:
    requests.post('http://127.0.0.1:5002/upload_video', data=f)