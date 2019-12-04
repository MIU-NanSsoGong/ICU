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
      seperate(data)
      return "god young soo"

def seperate(data):
   data_json = json.loads(data)
   cmd = data_json["cmd"]
   if cmd == u'2':
      print("cheer up")

   data_json2 = json.loads(data, encoding='utf-8')
   if cmd == "2":
      print("your grade is A+")


if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5000, debug=True)


