import 'package:flutter/material.dart';
import 'package:flutter_first_app/domain/Book.dart';
import 'package:flutter_first_app/widgets/MainScreen.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Goodreads',
      theme: ThemeData(
        primaryColor: Colors.green,
      ),
      home: MainScreen(
        books: generateBooks(),
      ),
    );
  }
}


List<Book> generateBooks() {
  List<Book> books = <Book>[];

  books.add(Book("Furia Rosie", "Pierce Broen", "In viitor, populaţia lumii e împărţită în clase având funcţii strict și clar definite, iar fiecărei clase îi corespunde câte o culoare. Darrow e un Roşu şi un Sondor al Iadului care lucrează în adâncul minelor de pe Marte pentru a face suprafaţa planetei locuibilă. La fel ca toţi cei din neamul lui, trudeşte din greu pentru a oferi un viitor mai bun generaţiilor următoare. Darrow va descoperi însă destul de repede că umanitatea ajunsese demult să populeze planeta Marte, iar cei ca el sunt ţinuţi drept sclavi de o clasă conducătoare decadentă, cea Aurie. Singurul mod în care se poate face dreptate în această societate abuzivă este ca Darrow să se infiltreze în mijlocul Auriilor, devenind unul dintre ei. ", 5));
  books.add(Book("Furia Aurie", "Pierce Broen", "blabla", 5));
  books.add(Book("Furia Diminetii", "Pierce Broen", "blablabla", 5));
  books.add(Book("Dracula", "Bram Stolker","blabla", 4));

  return books;
}


//
//class RandomWords extends StatefulWidget {
//  @override
//  RandomWordsState createState() => new RandomWordsState();
//}
//
//class RandomWordsState extends State<RandomWords> {
//  final List<Book> _books = <Book>[];
//  final TextStyle _biggerFont = const TextStyle(fontSize: 18.0);
//  final TextStyle _normalFont = const TextStyle(fontSize: 12.0);
//

//  @override
//  void initState() {
//    super.initState();
//    _books.add(new Book("Furia Rosie", "Pierce Broen", 5));
//    _books.add(new Book("Furia Aurie", "Pierce Broen", 5));
//    _books.add(new Book("Furia Diminetii", "Pierce Broen", 5));
//    _books.add(new Book("Dracula", "Bram Stolker", 4));
//  }
//
//  @override
//  Widget build(BuildContext context) {
//    return new Scaffold(
//      appBar: new AppBar(
//        title: const Text('Goodreads')
//      ),
//      body: _buildSuggestions(),
//    );
//  }
//
//  Widget _buildSuggestions() {
//    return new ListView.builder(
//        padding: const EdgeInsets.all(16.0),
//        itemBuilder: (BuildContext _context, int i) {
//          if (i.isOdd) {
//            return const Divider();
//          }
//          final int index = i ~/ 2;
//          return _buildRow(_books[index]);
//        });
//  }
//
//  Widget _buildRow(Book book) {
//
//    return new ListTile(
//      title: new Text(
//        book.title,
//        style: _biggerFont,
//      ),
//      subtitle: new Text(
//        book.author,
//        style: _normalFont
//      ),
//      trailing: new Icon(
//        Icons.edit,
//        color: Colors.green
//      ),
//      onTap: () => _openEdit(book)
//    );
//  }
//
//  void _openEdit(Book book) {
//
//  }

//  void _pushSaved() {
//    Navigator.of(context).push(
//      new MaterialPageRoute<void>(
//        builder: (BuildContext context) {
//          final Iterable<ListTile> tiles = _saved.map(
//                (WordPair pair) {
//              return new ListTile(
//                title: new Text(
//                  pair.asPascalCase,
//                  style: _biggerFont,
//                ),
//              );
//            },
//          );
//          final List<Widget> divided = ListTile
//              .divideTiles(
//            context: context,
//            tiles: tiles,
//          )
//              .toList();
//          return new Scaffold(
//            appBar: new AppBar(
//              title: const Text('Saved Suggestions'),
//            ),
//            body: new ListView(children: divided),
//          );
//        },
//      ),
//    );
//  }
//}