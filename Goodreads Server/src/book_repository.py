from pymongo import MongoClient
from bson.objectid import ObjectId # For ObjectId to work

class BookRepository:

    def __init__(self):
        self.client = MongoClient('localhost',27017)
        self.db = self.client.goodreads
        self.books = self.db.books

    def getAll(self):
        return [ {
                    'id' : str(book['_id']),
                    'title' : book['title'],
                    'author' : book['author'],
                    'description' : book['description'],
                    'rating': book['rating']
                } for book in self.books.find()]

    def add(self, book):
        book = { key : book[key] for key in book }
        book['rating'] = int(book['rating']) 
        self.books.insert_one(book)
        return 200

    def update(self, id, book):
        self.books.update({ "_id" : ObjectId(id) }, book)
        return 200

    def delete(self, id):
        self.books.remove(ObjectId(id))
        return 200
