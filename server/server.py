from flask import Flask, render_template, request, jsonify
from werkzeug import secure_filename
from flask_restful import reqparse, abort, Api, Resource

app = Flask(__name__)
api = Api(app)


@app.route('/fileUpload', methods = ['GET', 'POST'])
def upload_file():
   if request.method == 'POST':
      #result = request.get_json
      #print(jsonify(result))
      f = request.files['file']
      f.save(secure_filename(f.filename))
      return 'uploads directory -> file upload success!'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5000, debug=True)


