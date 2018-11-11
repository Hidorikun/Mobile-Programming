package com.example.george.firstapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.Editable
import kotlinx.android.synthetic.main.activity_detail.*

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        val id = intent.getIntExtra("id", 1)

        init(id)

        fab.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra("id", id)
            }

            it.context.startActivity(intent)
        }
    }

    fun init(id: Int) {
        title = id.toString()

        val book = BookRepository.get(id)

        if (book != null) {
            edit_title.text = Editable.Factory.getInstance().newEditable(book.title)
            edit_description.text = Editable.Factory.getInstance().newEditable(book.description)
            edit_stars.text = Editable.Factory.getInstance().newEditable(book.rating.toString())
        }
    }
}
