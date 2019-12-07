import json
import os
video_busy = 0

icu_path = os.environ['icu_path']

def seperate(data):
   #data_json = json.loads(data)
   cmd = data["cmd"]   
   number = data["number"]
   if cmd == "1":
      list_file = open(installed_path + 'server/client_list/list.json', 'r').read()
      client_list = json.loads(list_file)
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')

      list_file = open('./client_list/list.json', 'w')
      list_file.write(data)
      list_file.close()
      print("cheer up")
      return "1"
 
   if cmd == "2":
      list_file = open(installed_path + 'server/client_list/list.json', 'r').read()
      client_list = json.loads(list_file)
      try:
         if client_list[number]:
            print(client_list[number])
            return client_list[number]
      except:
         print("fdsfd")
         return "-1"
      print("your grade itts A+")
   
   if cmd == "3":
      message = open(installed_path + 'server/message/message.json', 'r').read()
      client_list = json.loads(message)
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')

      message = open(installed_path + 'server/message/message.json', 'w')
      message.write(data)
      message.close()
      print("cheer up")
      return '1'

   if cmd == '20':
      global video_busy
      #if (not video_busy) or (not os.path.isfile(installed_path + "server/upload_video.avi")):
      if (not video_busy) or (not os.path.isfile(icu_path + "server/upload_video.avi")):
         video_busy = 1
         return '1'
      else:
         return '0'

def release_busy():
   global video_busy
   video_busy = 0


"""def access(data):
   data_json = json.loads(data)
   number = data_json["number"]
   n=1
   while n<=1000:
"""
   