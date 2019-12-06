from flask import Flask, request
from werkzeug import secure_filename
from werkzeug.datastructures import FileStorage
from flask_restful import reqparse, abort, Api, Resource
import json
from pprint import pprint
import interpreter
import os

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
      interpreter.seperate(data, number)
      return "god young soo"
"""
@app.route('/video', methods = ['POST'])
def get_client_video():
   if request.method == 'POST':
      binary = request.content
      with open("good.jpg" 'wb') as s:
         s.write(binary)
"""
@app.route('/upload_video', methods = ['POST'])
def get_client_video():
   FileStorage(request.stream).save(os.path.join(os.getcwd(), "upload_video.avi"))
   return 'OK'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5002, debug=True)