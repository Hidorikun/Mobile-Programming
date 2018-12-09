package com.example.george.firstapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.Editable
import android.util.Log
import io.realm.Realm
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")

        realm = Realm.getDefaultInstance()

        init(id)

        fab.setOnClickListener {
            this.saveBook(id)
            super.onBackPressed()
        }
    }

    private fun saveBook(id: String) {
        val book = realm.where<Book>().equalTo("id", id).findFirst()
        if (book != null) {
            realm.executeTransaction {
                book.title = edit_title.text.toString()
                book.description = edit_description.text.toString()
                book.rating = edit_stars.text.toString().toInt()
                book.author = edit_author.text.toString()
            }
        }
    }

    fun init(id: String) {
        title = id

        val book = realm.where<Book>().equalTo("id", id).findFirst()

        Log.d("EDIT PAGE", book.toString())

        if (book != null) {
            edit_title.text = Editable.Factory.getInstance().newEditable(book.title)
            edit_description.text = Editable.Factory.getInstance().newEditable(book.description)
            edit_stars.text = Editable.Factory.getInstance().newEditable(book.rating.toString())
            edit_author.text = Editable.Factory.getInstance().newEditable(book.author.toString())
        }
    }
}
