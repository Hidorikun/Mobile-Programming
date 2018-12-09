import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/utils/database_helper.dart';
import 'package:flutter_first_app/utils/server_helper.dart';
import 'package:flutter_first_app/widgets/book_detail.dart';
import 'package:sqflite/sqflite.dart';

class BookList extends StatefulWidget {
  @override
  _BookListState createState() => _BookListState();
}

class _BookListState extends State<BookList> {
   DatabaseHelper databaseHelper = DatabaseHelper();
   ServerHelper serverHelper = ServerHelper();

   List<Book> books;
   List<Book> booksToDelete;
   int count = 0;

  @override
  Widget build(BuildContext context) {
    final title = 'GoodReads';

    if (books == null) {
      books = List<Book>();
      booksToDelete = List<Book>();
      updateListView();
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
        actions: <Widget>[
          IconButton(
          icon: Icon(Icons.refresh),
            onPressed: ()  {
              print("SYNCHING...");
//              setState(() {
//
//              });
              serverHelper.synchronize(booksToDelete);
              print("update list view");
              updateListView();
            }
          )
        ],
      ),
      body: ListView.builder(
        itemCount: books.length,
        itemBuilder: (BuildContext context, int index) {
          return ListTile (
            title: Text('${books[index].title}'),
            subtitle: Text('${books[index].author}'),
            trailing: GestureDetector(
              child: Icon(Icons.delete),
              onTap: () => _deleteBook(context, books[index]),
            ),
            onTap: () => _navigateToDetail(books[index], 'Edit Book'),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
          backgroundColor: Theme.of(context).primaryColor,
          child: Icon(Icons.add),
          onPressed: () => _navigateToDetail(Book('', '', '', 0), 'Add Book')
      ),
    );
  }


  void updateListView() async {
    final Future<Database> dbFuture = databaseHelper.initializeDatabase();
    dbFuture.then((database) {
      Future<List<Book>> booksFuture = databaseHelper.getBookList();
      booksFuture.then((books) {
        setState(() {
          this.books = books;
          this.count = books.length;
        });
      });
    });
  }

   void _deleteBook(BuildContext context, Book book) async {
      int result = await databaseHelper.deleteBook(book.id);
      booksToDelete.add(book);

      if (result != 0) {
        _showSnackBar(context, 'Book deleted successfully');
        await updateListView();
      }
   }

   void _showSnackBar(BuildContext context, String message) {

     final snackBar = SnackBar(content: Text(message));
     Scaffold.of(context).showSnackBar(snackBar);
   }

   void _navigateToDetail(Book book, String title) async {
     bool result = await Navigator.of(context).push(MaterialPageRoute(
       builder: (BuildContext context) {
         return new BookDetail(book, title);
       }
     ));

     if (result == true) {
       await updateListView();
     }
   }
}