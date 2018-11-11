package com.example.george.firstapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_edit.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id", 1)

        init(id)

        btn_edit.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java).apply {
                putExtra("id", id)
            }

            it.context.startActivity(intent)
        }
    }

    fun init(id: Int) {
        title = id.toString()

        val book = BookRepository.get(id)

        txt_title.text = book?.title
        txt_description.text = book?.description
        txt_stars.text = book?.rating.toString()
    }
}
