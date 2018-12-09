class Book {
  String id;
  String title;
  String author;
  String description;
  int rating;

  Book(this.title, this.author, this.description, this.rating);
  Book.withId(this.id, this.title, this.author, this.description, this.rating);

  Map<String, dynamic>  toMap() {
    var map = Map<String, dynamic>();

    if (id != null) {
      map['id'] = id;
    }
    map['title'] = title;
    map['description'] = description;
    map['author'] = author;
    map['rating'] = rating.toString();

    return map;
  }

  Book.fromMap(Map<String, dynamic> map) {
    this.id = map['id'].toString();
    this.title = map['title'];
    this.description = map['description'];
    this.author = map['author'];
    this.rating = int.parse(map['rating'].toString());
  }
}