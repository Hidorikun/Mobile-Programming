package com.example.george.firstapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.Editable
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_detail.*

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        val id = intent.getIntExtra("id", 1)

        realm = Realm.getDefaultInstance()

        init(id)

        fab.setOnClickListener {
            this.saveBook(id)
            super.onBackPressed()
        }
    }

    private fun saveBook(id: Int) {
        val book = realm.where<Book>().equalTo("id", id).findFirst()
        if (book != null) {
            realm.executeTransaction { _ ->
                book.title = edit_title.text.toString()
                book.description = edit_description.text.toString()
                book.rating = edit_stars.text.toString().toInt()
            }
        }
    }

    fun init(id: Int) {
        title = id.toString()

        val book = realm.where<Book>().equalTo("id", id).findFirst()

        if (book != null) {
            edit_title.text = Editable.Factory.getInstance().newEditable(book.title)
            edit_description.text = Editable.Factory.getInstance().newEditable(book.description)
            edit_stars.text = Editable.Factory.getInstance().newEditable(book.rating.toString())
        }
    }
}
