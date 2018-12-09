package com.example.george.firstapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Book(
    @PrimaryKey var id: String? = "0",
    var title: String? = "title",
    var author: String? = "author",
    var rating: Int? = 0,
    var description: String? = "description"
): RealmObject() {

}