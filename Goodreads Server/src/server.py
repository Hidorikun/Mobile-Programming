# FLASK_APP=server.py flask run --host=0.0.0.0
from book_repository import BookRepository

from flask import Flask
from flask import request
from flask import jsonify

import sys

app = Flask(__name__)

bookRepo = BookRepository()
#
# bookRepo.add({'title': 'adam si eva', 'author': 'L.Rebreanu', 'description' : 'o carte', 'rating' : 4})
# bookRepo.add({'title': 'ion', 'author': 'L.Rebreanu', 'description' : 'o carte', 'rating' : 4})
# bookRepo.add({'title': 'padurea spanzuratilor', 'author': 'L.Rebreanu', 'description' : 'o carte', 'rating' : 4})
# bookRepo.add({'title': 'morometii', 'author': '??', 'description' : 'o carte', 'rating' : 4})

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/books", methods=['GET', 'POST'])
def books():
    if request.method == 'GET':
        return jsonify(bookRepo.getAll())
    elif request.method == 'POST':
        print(request.form, file=sys.stderr)
        return jsonify(bookRepo.add(request.form))

@app.route('/books/<id>', methods=['PUT', 'DELETE'])
def books_id(id):
    if request.method == 'PUT':
        print(request.form, file=sys.stderr)
        return jsonify(bookRepo.update(id, request.form))
    elif request.method == 'DELETE':
        return jsonify(bookRepo.delete(id))
