import os
os.environ['icu_path'] = os.path.expanduser("~") + "/.icu_1.0/"

from flask import Flask, request
from werkzeug import secure_filename
from werkzeug.datastructures import FileStorage
from flask_restful import Api
import json
import interpreter
from icu.ML import MLmain
app = Flask(__name__)
api = Api(app)

@app.route('/', methods = ['POST'])
def upload_file():
   if request.method == 'POST':
      raw_data = request.json
      return interpreter.seperate(raw_data)

@app.route('/upload_video', methods = ['POST'])
def get_client_video():
   FileStorage(request.stream).save(os.path.join(os.environ['icu_path'] + "server/", "upload_video.avi"))
   number = interpreter.get_num()
   MLmain.MLseq(number)
   interpreter.release_busy()
   return '1'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5002, debug=True)