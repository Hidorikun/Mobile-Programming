package com.gecopuf.george.goodreads.book

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.gecopuf.george.goodreads.R
import com.gecopuf.george.goodreads.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_book_list.*

class BookListActivity : AppCompatActivity() {
    private val TAG = "BookListActivity"

    private var mAdapter: BookAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    companion object {
        var animationItem: Int = 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        firestoreDB = FirebaseFirestore.getInstance()

        loadBooksList()

        firestoreListener = firestoreDB!!.collection("books")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e(TAG, "Listen failed!", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val booksList = mutableListOf<Book>()

                for (doc in querySnapshot!!) {
                    val book = doc.toObject(Book::class.java)
                    book.id = doc.id
                    booksList.add(book)
                }

                mAdapter = BookAdapter(booksList, applicationContext, firestoreDB!!)
                book_list.adapter = mAdapter
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadBooksList() {
        firestoreDB!!.collection("books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val booksList = mutableListOf<Book>()

                    for (doc in task.result!!) {
                        val book = doc.toObject<Book>(Book::class.java)
                        book.id = doc.id
                        booksList.add(book)
                    }

                    mAdapter = BookAdapter(booksList, applicationContext, firestoreDB!!)
                    book_list.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        animationItem = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_animations, menu)
        return true
    }
}
