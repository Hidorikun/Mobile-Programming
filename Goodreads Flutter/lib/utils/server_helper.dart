import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/utils/database_helper.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ServerHelper {

  static ServerHelper _databaseHelper; // Singleton ServerHelper
  String url = "http://10.152.1.165:5000";

  ServerHelper._createInstance(); // Named constructor to create instance of ServerHelper
  DatabaseHelper local = DatabaseHelper();

  factory ServerHelper() {
    if (_databaseHelper == null) {
      _databaseHelper = ServerHelper
          ._createInstance(); // This is executed only once, singleton object
    }
    return _databaseHelper;
  }


  Future<bool> isActive() async {
    var response;

    try {
      response = await http.get(url);
    } catch (e) {
      return false;
    }

    return response != null && response.statusCode == 200;
  }

  // Insert Operation: Insert a Book object to database
  Future<int> insertBook(Book book) async {
    var body = book.toMap();
    body.remove('id');
    final response = await http.post(this.url + '/books', body: body);
    return response.statusCode;
  }

  // Update Operation: Update a Book object and save it to database
  Future<int> updateBook(Book book) async {
    final response = await http.put(
        this.url + '/books/' + book.id.toString(), body: book.toMap());
    return response.statusCode;
  }

  // Delete Operation: Delete a Book object from database
  Future<int> deleteBook(String id) async {
    final response = await http.delete(this.url + '/books/' + id.toString());
    return response.statusCode;
  }

  // Get number of Book objects in database
  Future<int> getCount() async {
    final response = await http.get(this.url + '/books');
    return json
        .decode(response.body)
        .length;
  }

  // Get the 'Map List' [ List<Map> ] and convert it to 'Book List' [ List<Book> ]
  Future<List<Book>> getBookList() async {
    final response = await http.get(this.url + '/books');

    var bookMapList = await json.decode(response.body);

    int count = bookMapList.length; // Count the number of map entries in db table

    print("maplist as i got it: " + bookMapList.toString());

    List<Book> bookList = List<Book>();
    // For loop to create a 'Book List' from a 'Map List'
    for (int i = 0; i < count; i++) {
      print(i.toString() + ": " + bookMapList[i].toString());
      print(Book.fromMap(bookMapList[i]).toString());

      bookList.add(Book.fromMap(bookMapList[i]));
    }

    return bookList;
  }

  void synchronize(List<Book> booksToDelete) async {
    var active = await this.isActive();

    if (!active) { print("not  active  server"); return; }

    print("server  active");

//    print('deleting all');
//    await local.deleteAll();
//    print('done deleting');

    print(booksToDelete);
    for (Book book in booksToDelete) {
      deleteBook(book.id);
    }

    booksToDelete.clear();

    List<Book> localBooks = await local.getBookList();
    List<Book> serverBooks = await this.getBookList();

    for (Book localBook in localBooks) {
      print(localBook);
      if (localBook.id.length < 4) {
        print("inserting to server: "  + localBook.toString());
        await this.insertBook(localBook);
      }else {
        await this.updateBook(localBook);
      }

      local.deleteBook(localBook.id);
    }

    serverBooks = await this.getBookList();

    for (Book book in serverBooks) {
      local.insertBook(book);
    }
  }
}



