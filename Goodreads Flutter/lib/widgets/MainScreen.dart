import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/widgets/AddScreen.dart';
import 'package:flutter_first_app/widgets/DetailScreen.dart';

class MainScreen extends StatefulWidget {
  final List<Book> books;

  MainScreen({Key key, @required this.books}) : super();

  @override
  _MainScreenState createState() => _MainScreenState(books: books);
}

class _MainScreenState extends State<MainScreen> {
   List<Book> books;

  _MainScreenState({Key key, @required this.books}) : super();

  @override
  Widget build(BuildContext context) {
    final title = 'GoodReads';

    void _deleteBook(Book book) {
      setState(() {
        books.remove(book);
      });
    }

    void _addBook(Book book) {
      books.add(book);
    }

    Future _showDialog() async {
      Book book = await Navigator.of(context).push(MaterialPageRoute<Book>(
        builder: (BuildContext context) {
          return new AddScreen();
        },
        fullscreenDialog: true
      ));

      if (book != null) {
        _addBook(book);
      }
    }

    Future _showEditDialog(Book book) async {
      Book newBook = await Navigator.of(context).push(MaterialPageRoute<Book>(
          builder: (BuildContext context) {
            return new DetailScreen(book: null);
          },
          fullscreenDialog: true
      ));

      if (book != null) {
        _deleteBook(book);
        _addBook(newBook);
      }
    }


    return new MaterialApp(
        title: title,
        theme: ThemeData(
          primaryColor: Colors.green,
        ),
        home: Scaffold(
          appBar: AppBar(
            title: Text(title),
          ),
          body: ListView.builder(
            itemCount: books.length,
            itemBuilder: (BuildContext context, int index) {
              return ListTile (
                title: Text('${books[index].title}'),
                subtitle: Text('${books[index].author}'),
                trailing: Icon(
                    Icons.delete
                ),
                onTap: () => _showEditDialog(books[index]),
                onLongPress: () => _deleteBook(books[index]),
              );
            },
          ),
          floatingActionButton: FloatingActionButton(
              backgroundColor: Colors.green,
              child: Icon(Icons.add),
              onPressed: _showDialog
          ),
        )
    );
  }

}