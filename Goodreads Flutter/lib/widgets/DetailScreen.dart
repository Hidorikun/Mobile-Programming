
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';

class DetailScreen extends StatefulWidget {
  Book book;

  DetailScreen({Key key, @required this.book}): super(key: key);

  @override
  State<StatefulWidget> createState() => AddBookState(book: book);
}

class AddBookState extends State<DetailScreen> {
  Book book;

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final authorController = TextEditingController();
  final ratingController = TextEditingController();

  AddBookState({Key key, @required this.book}): super();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Add Book'),
        actions: <Widget>[
          FlatButton(
            onPressed: () {
              Navigator.of(context)
                  .pop(new Book(
                    titleController.text,
                    authorController.text,
                    descriptionController.text,
                    int.parse(ratingController.text)
                  )
              );
            },
            child: Text('SAVE',
                style: Theme
                    .of(context)
                    .textTheme
                    .subhead
                    .copyWith(color: Colors.white)),
          )
        ],
      ),
      body: ListView(
        children: <Widget>[
          TextFormField(
            controller: titleController,
            decoration: InputDecoration(
                labelText: 'Title: '
            ),
            initialValue: book.title,
          ),
          TextFormField(
            controller: authorController,
            decoration: InputDecoration(
                labelText: 'Author: '
            ),

            initialValue: book.author,
          ),
          TextFormField(
            controller: descriptionController,
            decoration: InputDecoration(
                labelText: 'description: '
            ),

            initialValue: book.description,
          ),
          TextFormField(
            controller: ratingController,
            decoration: InputDecoration(
                labelText: 'Rating: '
            ),

            initialValue: book.rating.toString(),
          ),
        ],
      ),
    );
  }
}