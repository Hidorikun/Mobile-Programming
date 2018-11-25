import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/widgets/book_list.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Goodreads',
      theme: ThemeData(
        primaryColor: Colors.green,
      ),
      home: BookList(),
    );
  }
}

