import json
import os
from icu.ML import MLmain

video_busy = 0
icu_path = os.environ['icu_path']

def seperate(data):
   cmd = data["cmd"]   
   number = data["number"]
   if cmd == "0":
      list_file = open(icu_path + 'server/client_list/list.json', 'r+')
      try:
         client_list = json.loads(list_file)
      except TypeError:
         client_list = {}
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')
      list_file.write(data)
      list_file.close()
      return "1"
 
   if cmd == "1":
      list_file = open(icu_path + 'server/client_list/list.json', 'r').read()
      client_list = json.loads(list_file)
      try:
         if client_list[number]:
            return client_list[number]
      except:
         return "-1"
   
   if cmd == "2":
      message = open(icu_path + 'server/message/message.json', 'r').read()
      client_list = json.loads(message)
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')

      message = open(icu_path + 'server/message/message.json', 'w')
      message.write(data)
      message.close()
      print("cheer up")
      return '1'

   if cmd == '20':
      global video_busy
      global video_num
      if (not video_busy) or (not os.path.isfile(icu_path + "server/upload_video.avi")):
         video_busy = 1
         video_num = number
         return '1'
      else:
         return '0'

   if cmd == '21':
      MLmain.check_visitor()
      return '1'

   if cmd == '50':
      list_file = open(icu_path + 'server/client_list/list.json', 'r').read()
      return list_file

   if cmd == '51':
      ch_num = data['ch_num']
      ch_stat = data['stat']

      list_file_read = open(icu_path + 'server/client_list/list.json', 'r').read()
      client_list = json.loads(list_file_read)
      client_list[ch_num] = ch_stat
      data = json.dumps(client_list, encoding='utf-8')
      list_file = open(icu_path + 'server/client_list/list.json', 'w')
      list_file.write(data)
      list_file.close()
      return "1"

def release_busy():
   global video_busy
   video_busy = 0

def get_num():
   global video_num
   return video_num

"""def access(data):
   data_json = json.loads(data)
   number = data_json["number"]
   n=1
   while n<=1000:
"""
   