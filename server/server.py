import os
os.environ['icu_path'] = os.path.expanduser("~") + "/.icu_1.0/"

from flask import Flask, request
from werkzeug import secure_filename
from werkzeug.datastructures import FileStorage
from flask_restful import Api
import json
import interpreter
from icu.ML import extracter
app = Flask(__name__)
api = Api(app)

#installed_path = os.path.expanduser("~") + "/.icu_1.0/"

@app.route('/', methods = ['POST'])
def upload_file():
   if request.method == 'POST':
      raw_data = request.json
      #number = raw_data["number"]
      #file_dir = installed_path + "server/queue/" + number + ".json"
      #file = open(file_dir, 'w')
      #data = json.dumps(raw_data, encoding='utf-8')
      #file.write(data)
      #file.close()
      return interpreter.seperate(raw_data)#data->raw_data

@app.route('/upload_video', methods = ['POST'])
def get_client_video():
   FileStorage(request.stream).save(os.path.join(os.environ['icu_path'] + "server/", "upload_video.avi"))
   extracter.extract()
   interpreter.release_busy()
   return '1'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5002, debug=True)