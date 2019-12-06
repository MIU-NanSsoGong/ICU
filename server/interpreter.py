import json


def seperate(data, number):
   data_json = json.loads(data)
   cmd = data_json["cmd"]   
   if cmd == "1":
      list_file = open('./client_list/list.json', 'r').read()
      client_list = json.loads(list_file)
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')

      list_file = open('./client_list/list.json', 'w')
      list_file.write(data)
      list_file.close()
      print("cheer up")
 
   if cmd == "2":
      list_file = open('./client_list/list.json', 'r').read()
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
      message = open('./message/message.json', 'r').read()
      client_list = json.loads(message)
      client_list[number] = "0"  
      data = json.dumps(client_list, encoding='utf-8')

      message = open('./message/message,json', 'w')
      message.write(data)
      message.close()
      print("cheer up")



"""def access(data):
   data_json = json.loads(data)
   number = data_json["number"]
   n=1
   while n<=1000:
"""
   