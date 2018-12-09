import 'package:flutter_first_app/domain/Book.dart';
import 'package:sqflite/sqflite.dart';
import 'dart:async';
import 'dart:io';
import 'package:path_provider/path_provider.dart';

class DatabaseHelper {

  static DatabaseHelper _databaseHelper;    // Singleton DatabaseHelper
  static Database _database;                // Singleton Database

  String bookTable = 'book_table';
  String colId = 'id';
  String colTitle = 'title';
  String colDescription = 'description';
  String colRating = 'rating';
  String colAuthor = 'author';

  DatabaseHelper._createInstance(); // Named constructor to create instance of DatabaseHelper

  factory DatabaseHelper() {

    if (_databaseHelper == null) {
      _databaseHelper = DatabaseHelper._createInstance(); // This is executed only once, singleton object
    }
    return _databaseHelper;
  }

  Future<Database> get database async {

    if (_database == null) {
      _database = await initializeDatabase();
    }
    return _database;
  }

  Future<Database> initializeDatabase() async {


    // Get the directory path for both Android and iOS to store database.
    Directory directory = await getApplicationDocumentsDirectory();
    String path = directory.path + 'books2.db';

    // Open/create the database at a given path
    var bookDatabase = await openDatabase(path, version: 1, onCreate: _createDb);
//    print(bookDatabase.toString());
    return bookDatabase;
  }

  void _createDb(Database db, int newVersion) async {

//    print('creating new database');

    await db.execute('CREATE TABLE $bookTable('
        '$colId STRING PRIMARY KEY,'
        '$colTitle TEXT, '
        '$colAuthor TEXT, '
        '$colDescription TEXT, '
        '$colRating INTEGER)'
        '');
  }

  // Fetch Operation: Get all book objects from database
  Future<List<Map<String, dynamic>>> getBookMapList() async {
    Database db = await this.database;

//		var result = await db.rawQuery('SELECT * FROM $bookTable order by $colPriority ASC');
    var result = await db.query(bookTable);
    return result;
  }

  Future<String> generateId() async {
    List<Book> books = await getBookList();

    int id = 0;

    for (Book book in books) {
      try {
        int k = int.parse(book.id);
        if (k > id) {
          id = k;
        }
      }catch (e) {

      }
    }

    return (id + 1).toString();
  }

  // Insert Operation: Insert a Book object to database
  Future<int> insertBook(Book book) async {
//    print('waiting database');
    Database db = await this.database;
//    print('got database');
//    print('waiting insert');

    if (book.id == null)
      book.id = await generateId();

    var result = await db.insert(bookTable, book.toMap());
    print('inserted to local');
    return result;
  }

  // Update Operation: Update a Book object and save it to database
  Future<int> updateBook(Book book) async {
    var db = await this.database;
    var result = await db.update(bookTable, book.toMap(), where: '$colId = ?', whereArgs: [book.id]);
    return result;
  }

  // Delete Operation: Delete a Book object from database
  Future<int> deleteBook(String id) async {
    var db = await this.database;
    int result = await db.rawDelete('DELETE FROM $bookTable WHERE $colId = \'$id\'');
    return result;
  }

  // Get number of Book objects in database
  Future<int> getCount() async {
    Database db = await this.database;
    List<Map<String, dynamic>> x = await db.rawQuery('SELECT COUNT (*) from $bookTable');
    int result = Sqflite.firstIntValue(x);
    return result;
  }

  // Get the 'Map List' [ List<Map> ] and convert it to 'Book List' [ List<Book> ]
  Future<List<Book>> getBookList() async {

    var bookMapList = await getBookMapList(); // Get 'Map List' from database
    int count = bookMapList.length;         // Count the number of map entries in db table

    print(bookMapList.toString());

    List<Book> bookList = List<Book>();
    // For loop to create a 'Book List' from a 'Map List'
    for (int i = 0; i < count; i++) {

      print(bookMapList[i].toString());
      bookList.add(Book.fromMap(bookMapList[i]));
    }

    return bookList;
  }

  Future<int> deleteAll() async {
    var db = await this.database;
    int result = await db.rawDelete('DELETE FROM $bookTable');
    return result;
  }
}





