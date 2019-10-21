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

      file = open(number+".json", 'w')
      file.write(data)
      data = json.loads(data)
      file.close()
      return 'uploads directory -> file upload success!'
  
"""   list_file = open('./client_list/list.json', 'r').read()
   client_list = json.loads(list_file)
   try:
      if not client_list[number]:
         return 'This number is already enrolled'
   except:
      list_file = open('./client_list/list.json', 'w')
      client_list[number].append("0")
      data = json.dumps(client_list, encoding='utf-8')
      #list_file.write(data_)
   return 'uploads directory -> file upload success!'
"""


if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5000, debug=True)


