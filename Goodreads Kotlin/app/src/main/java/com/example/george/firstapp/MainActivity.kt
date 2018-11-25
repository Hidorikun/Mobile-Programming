package com.example.george.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.george.firstapp.R.id.btn_refresh
import io.realm.Realm
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_book.*

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

//        realm.executeTransaction { realm ->
//            realm.deleteAll()
//        }
//
//        realm.executeTransaction { realm ->
//            val book = realm.createObject<Book>(0)
//            book.description = "ceva descriere random"
//            book.title = "ceva titlu pe acolo"
//            book.rating = 3
//        }

        fab.setOnClickListener {
            val dialog = AddDialog(this)
            dialog.show()
        }



        adapter = BookAdapter(realm)
        rv_item_list.layoutManager = LinearLayoutManager(this)
        rv_item_list.adapter = adapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_refresh -> {
            adapter.notifyDataSetChanged()
            Toast.makeText(this.baseContext, "Refreshed", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
