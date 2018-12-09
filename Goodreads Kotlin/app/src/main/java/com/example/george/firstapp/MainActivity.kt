package com.example.george.firstapp

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var adapter: BookAdapter
    private lateinit var booksToDelete: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        Realm.deleteRealm(Realm.getDefaultConfiguration())
        // This will automatically trigger the migration if needed
        realm = Realm.getDefaultInstance()
        booksToDelete= mutableListOf()

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

        adapter = BookAdapter(realm, booksToDelete)
        rv_item_list.layoutManager = LinearLayoutManager(this)
        rv_item_list.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

    @SuppressLint("CheckResult")
    fun synchronize() {
        var realm = Realm.getDefaultInstance()
        val networkApiAdapter = NetworkAPIAdapter.instance

        val serverBooks = networkApiAdapter.fetchAll()
        val localBooks = realm.where<Book>().findAll()
//
//        for (unwantedBook in booksToDelete) {
//            Log.d("DELETING", unwantedBook.toString())
//            networkApiAdapter.delete(unwantedBook.id!!)
//        }

        for (localBook in localBooks) {
            val id = localBook.id

            Log.d("GEORGE", localBook.toString())
            if (id != null) {
                if (id.length < 4){
                    Log.d("LENGTH < 4", id)
                    networkApiAdapter.insert(localBook)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {}, {}, {
                            Log.d("INSERT FINISHED", "gata boss")
                        })
                }else {
                    networkApiAdapter.update(id, localBook)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {}, {}, {
                            Log.d("UPDATE FINISHED", "gata boss")
                        })
                }
            }
        }

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }

        for (serverBook in serverBooks) {
            realm.executeTransaction { realm ->
                val book = realm.createObject<Book>(serverBook.id)
                book.title = serverBook.title
                book.description = serverBook.description
                book.rating = serverBook.rating
                book.author = serverBook.author
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_refresh -> {
            doAsync {
                synchronize()
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }

            }
            Toast.makeText(this.baseContext, "Synchronized", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
