package com.gecopuf.george.goodreads.book

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gecopuf.george.goodreads.R
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        description.text = intent.extras.getString("description")
        author.text = intent.extras.getString("author")
        titleBook.text = intent.extras.getString("title")
        rating.text = intent.extras.getString("rating")
    }
}
