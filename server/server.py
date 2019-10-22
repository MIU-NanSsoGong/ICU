from flask import Flask, request
from werkzeug import secure_filename
from flask_restful import reqparse, abort, Api, Resource
import json
from pprint import pprint


app = Flask(__name__)
api = Api(app)


@app.route('/UserEnroll', methods = ['POST'])
def upload_file():
   if request.method == 'POST':
      raw_data = request.json
      number = raw_data["number"]
      data = json.dumps(raw_data, encoding='utf-8')
      file = open("./queue/"+number+".json", 'w')
      file.write(data)
      #data = json.loads(data)
      file.close()

   list_file = open('./client_list/list.json', 'r').read()
   client_list = json.loads(list_file)
   try:
      if client_list[number]:
         return client_list[number]
   except:
      list_file = open('./client_list/list.json', 'w')
      client_list[number] = "0"
      data = json.dumps(client_list, encoding='utf-8')
      list_file.write(data)
   return 'uploads directory -> file upload success!'

@app.route('/ServerAccess', methods = ['POST'])
def server_access():
   if request.method == 'POST':
      raw_data = request.json
      number = raw_data["number"]
      data = json.dumps(raw_data, encoding='utf-8')
      file = open("./queue/"+number+".json", 'w')
      file.write(data)
      file.close()
      list_file = open('./client_list/list.json', 'r').read()
      client_list = json.loads(list_file)
      try:
         if client_list[number]:
            print(client_list[number])
            return client_list[number]
      except:
         return "-1"



if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5000, debug=True)


