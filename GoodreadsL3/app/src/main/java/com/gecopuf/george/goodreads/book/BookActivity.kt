package com.gecopuf.george.goodreads.book

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gecopuf.george.goodreads.R
import com.gecopuf.george.goodreads.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    private val TAG = "AddBookActivity"

    private var firestoreDB: FirebaseFirestore? = null
    internal var id: String = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateBookId")

            title_book.setText(bundle.getString("UpdateBookTitle"))
            author_book.setText(bundle.getString("UpdateBookAuthor"))
            description_book.setText(bundle.getString("UpdateBookDescription"))
            rating_book.setText(bundle.getString("UpdateBookRating"))
        }

        edit_button.visibility = View.GONE
        add_button.visibility = View.GONE

        if (id.isNotEmpty()) {
            edit_button.visibility = View.VISIBLE
            add_button.visibility = View.GONE
        } else {
            edit_button.visibility = View.GONE
            add_button.visibility = View.VISIBLE
        }


        add_button.setOnClickListener {
            val title = title_book.text.toString()
            val author = author_book.text.toString()
            val description = description_book.text.toString()
            val rating = rating_book.text.toString()

            addBook(title, author, description, rating)
            finish()
        }

        edit_button.setOnClickListener {
            val title = title_book.text.toString()
            val author = author_book.text.toString()
            val description = description_book.text.toString()
            val rating = rating_book.text.toString()

            updateBook(id, title, author, description, rating)
            finish()
        }
    }

    private fun checkFields(title: String, author: String, description: String, rating: String): Boolean {
        if (title.isEmpty()) {
            Toast.makeText(this@BookActivity, "Fill title field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (author.isEmpty()) {
            Toast.makeText(this@BookActivity, "Fill author field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (description.isEmpty()) {
            Toast.makeText(this@BookActivity, "Fill description field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (rating.isEmpty()) {
            Toast.makeText(this@BookActivity, "Fill rating field!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun updateBook(
        id: String,
        title: String,
        author: String,
        description: String,
        rating: String
    ) {


        val book = Book(id, title, author, description, rating.toInt()).toMap()

        firestoreDB!!.collection("books")
            .document(id)
            .set(book)
            .addOnSuccessListener {
                Log.e(TAG, "Book document update successful!")
                Toast.makeText(applicationContext, "Book has been updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Book document", e)
                Toast.makeText(applicationContext, "Book could not be updated!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBook(title: String, author: String, description: String, rating: String) {

        val book = Book(title, author, description, rating.toInt()).toMap()

        firestoreDB!!.collection("books")
            .add(book)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(applicationContext, "Book has been added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Book document", e)
                Toast.makeText(applicationContext, "Book could not be added!", Toast.LENGTH_SHORT).show()
            }
    }
}
