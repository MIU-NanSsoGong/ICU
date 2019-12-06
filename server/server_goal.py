from flask import Flask, request
from werkzeug import secure_filename
from flask_restful import reqparse, abort, Api, Resource
import json
from pprint import pprint
import ast

app = Flask(__name__)
api = Api(app)


@app.route('/', methods = ['POST'])
def upload_file():
   if request.method == 'POST':
      raw_data = request.json
      number = raw_data["number"]
      file_dir = "./queue/"+number+".json"
      file = open(file_dir, 'w')
      data = json.dumps(raw_data, encoding='utf-8')
      file.write(data)
      file.close()
      seperate(data, number)
      return "god young soo"

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
   
   

if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5002, debug=True)


