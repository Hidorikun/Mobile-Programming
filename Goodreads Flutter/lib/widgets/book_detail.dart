import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/utils/database_helper.dart';

class BookDetail extends StatefulWidget {
  final Book book;
  final String title;

  BookDetail(this.book, this.title);

  @override
  State<StatefulWidget> createState() => _BookDetailState(this.book, this.title);
}

class _BookDetailState extends State<BookDetail> {
  Book book;
  String title;

  DatabaseHelper helper = DatabaseHelper();

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final authorController = TextEditingController();
  final ratingController = TextEditingController();

  _BookDetailState(this.book, this.title);

  @override
  Widget build(BuildContext context) {

    TextStyle textStyle = Theme.of(context).textTheme.title;

    titleController.text = book.title;
    descriptionController.text = book.description;
    authorController.text = book.author;
    ratingController.text = book.rating.toString();

    return WillPopScope(
      onWillPop: () {
        moveToLastScreen();
      },
      child: Scaffold(
          appBar: AppBar(
            title: Text(title),
            leading: IconButton(icon: Icon(
              Icons.arrow_back),
              onPressed: () {
                moveToLastScreen();
              },
            )),
          body: Padding(
            padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
            child:  ListView(
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.only(top: 15.0),
                  child:  TextField(
                    controller: titleController,
                    style: textStyle,
                    onChanged: (value) {
                      updateTitle();
                    },
                    decoration: InputDecoration(
                        labelText: 'Title',
                        labelStyle: textStyle,
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)
                        )
                    ),
                  ),
                ),

                Padding(
                    padding: EdgeInsets.only(top: 15.0),
                    child:TextField(
                      controller: authorController,
                      style: textStyle,
                      onChanged: (value) {
                        updateAuthor();
                      },
                      decoration: InputDecoration(
                          labelText: 'Author',
                          labelStyle: textStyle,
                          border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5.0)
                          )
                      ),
                    ),
                ),

                Padding(
                    padding: EdgeInsets.only(top: 15.0),
                    child: TextField(
                      controller: descriptionController,
                      style: textStyle,
                      onChanged: (value) {
                        updateDescription();
                      },
                      decoration: InputDecoration(
                          labelText: 'Description',
                          labelStyle: textStyle,
                          border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5.0)
                          )
                      ),
                    ),
                ),


                Padding(
                    padding: EdgeInsets.only(top: 15.0),
                    child: TextField(
                      controller: ratingController,
                      style: textStyle,
                      onChanged: (value) {
                        updateRating();
                      },
                      decoration: InputDecoration(
                          labelText: 'Rating',
                          labelStyle: textStyle,
                          border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5.0)
                          )
                      ),
                    ),
                ),


                Padding(
                    padding: EdgeInsets.only(top: 15.0),
                    child: Row(
                      children: <Widget>[


                        Expanded(
                          child: RaisedButton(
                            color: Colors.red,
                            textColor: Colors.white,
                            child: Text(
                              'Delete',
                              textScaleFactor: 1.5,
                            ),
                            onPressed: () {
                              setState(() {
                                debugPrint("Delete button clicked");
                                _delete();
                              });
                            },
                          ),
                        ),
                        Container(width: 5.0,),

                        Expanded(
                          child: RaisedButton(
                            color: Colors.green,
                            textColor: Colors.white,
                            child: Text(
                              'Save',
                              textScaleFactor: 1.5,
                            ),
                            onPressed: () {
                              setState(() {
                                debugPrint("Save button clicked");
                                _save();
                              });
                            },
                          ),
                        ),

                      ],
                    ),
                ),
              ],
            ),
          )
      )
    );
  }

  void moveToLastScreen() {
    Navigator.pop(context, true);
  }

  void updateTitle() {
    book.title = titleController.text.toString();
  }

  void updateDescription() {
    book.description = descriptionController.text.toString();
  }

  void updateRating() {
    book.rating = int.parse(ratingController.text.toString());
  }

  void updateAuthor() {
    book.author = authorController.text.toString();
  }

  void _save() async {
    moveToLastScreen();

    int result;
    if (book.id != null){
      result = await helper.updateBook(book);
    } else {
      result = await helper.insertBook(book);
    }

    if (result != 0) { 
      _showAlertDialog('Status', 'Book Saved Successfully');
    } else { 
      _showAlertDialog('Status', 'Problem Saving Book');
    }
  }

  void _delete() async {

    moveToLastScreen();

    if (book.id == null) {
      _showAlertDialog('Status', 'No Book was deleted');
      return;
    }

    // Case 2: User is trying to delete the old book that already has a valid ID.
    int result = await helper.deleteBook(book.id);
    if (result != 0) {
      _showAlertDialog('Status', 'Book Deleted Successfully');
    } else {
      _showAlertDialog('Status', 'Error Occured while Deleting Book');
    }
  }
  
  void _showAlertDialog(String title, String message) {

    AlertDialog alertDialog = AlertDialog(
      title: Text(title),
      content: Text(message),
    );
    showDialog(
        context: context,
        builder: (_) => alertDialog
    );
  }

}