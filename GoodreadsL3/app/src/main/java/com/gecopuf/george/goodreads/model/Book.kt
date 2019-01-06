package com.gecopuf.george.goodreads.model


class Book {
    var id: String? = null
    var title: String? = null
    var author: String? = null
    var description: String? = null
    var rating: Int? = null

    constructor() {}

    constructor(id: String, title: String, author: String, description: String, rating: Int) {
        this.id = id
        this.title = title
        this.author = author
        this.description = description
        this.rating = rating
    }

    constructor(title: String, author: String, description: String, rating: Int) {
        this.title = title
        this.author = author
        this.description = description
        this.rating = rating
    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["title"] = title!!
        result["author"] = author!!
        result["description"] = description!!
        result["rating"] = rating!!

        return result
    }
}