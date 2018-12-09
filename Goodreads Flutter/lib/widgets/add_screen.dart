import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';

class AddScreen extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => AddBookState();
}

class AddBookState extends State<AddScreen> {

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final authorController = TextEditingController();
  final ratingController = TextEditingController();

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
          ),
          TextFormField(
            controller: authorController,
            decoration: InputDecoration(
                labelText: 'Author: '
            ),
          ),
          TextFormField(
            controller: descriptionController,
            decoration: InputDecoration(
                labelText: 'description: '
            ),
          ),
          TextFormField(
            controller: ratingController,
            decoration: InputDecoration(
                labelText: 'Rating: '
            ),
          ),
        ],
      ),
    );
  }

}