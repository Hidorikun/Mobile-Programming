class Book {
  int _id;
  String title;
  String author;
  String description;
  int rating;

  Book(this.title, this.author, this.description, this.rating);
  Book.withId(this._id, this.title, this.author, this.description, this.rating);

  get id => _id;

  Map<String, dynamic>  toMap() {
    var map = Map<String, dynamic>();

    if (_id != null) {
      map['id'] = _id;
    }
    map['title'] = title;
    map['description'] = description;
    map['author'] = author;
    map['rating'] = rating;

    return map;
  }

  Book.fromMap(Map<String, dynamic> map) {
    this._id = map['id'];
    this.title = map['title'];
    this.description = map['description'];
    this.author = map['author'];
    this.rating = map['rating'];
  }
}