package com.gecopuf.george.goodreads.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.firebase.firestore.FirebaseFirestore

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.Toast
import com.gecopuf.george.goodreads.R
import com.gecopuf.george.goodreads.model.Book
import kotlinx.android.synthetic.main.book_view.view.*

class BookAdapter(
    private val booksList: MutableList<Book>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore)

    : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: BookViewHolder

        if(convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.book_view, parent, false)
            viewHolder = BookViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as BookViewHolder
        }

        var book = this.getItem(position) as Book

        viewHolder.txtTitle.text = book.title
        viewHolder.txtRating.text = book.rating.toString()


        viewHolder.view.btnEdit.setOnClickListener { updateBook(book) }
        viewHolder.view.btnDelete.setOnClickListener { deleteBook(book.id!!, position) }
        viewHolder.view.setOnClickListener {
            val detailIntent = Intent(context, BookDetailActivity::class.java)
            detailIntent.putExtra("title", book.title)
            detailIntent.putExtra("author", book.author)
            detailIntent.putExtra("rating", book.rating.toString())
            detailIntent.putExtra("description", book.description)
            detailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(context, detailIntent, null)
        }

        val animation: Animation

        when (BookListActivity.animationItem) {
            R.id.fade -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                convertView!!.startAnimation(animation)
            }
            R.id.slideleft -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_left)
                convertView!!.startAnimation(animation)
            }
            R.id.slideup -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                convertView!!.startAnimation(animation)
            }
            R.id.shake -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.shake)
                convertView!!.startAnimation(animation)
            }
            R.id.scale -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                convertView!!.startAnimation(animation)
            }
        }


        return view!!
    }

    override fun getItem(position: Int): Any {
        return booksList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return booksList.count()
    }

    class BookViewHolder(val view: View) {
        var txtTitle: TextView = view.title
        var txtRating: TextView = view.rating

    }

    private fun updateBook(book: Book) {
        val intent = Intent(context, BookActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateBookId", book.id)
        intent.putExtra("UpdateBookTitle", book.title)
        intent.putExtra("UpdateBookRating", book.rating.toString())
        intent.putExtra("UpdateBookAuthor", book.author)
        intent.putExtra("UpdateBookDescription", book.description)
        context.startActivity(intent)
    }

    private fun deleteBook(id: String, position: Int) {
        firestoreDB.collection("books")
            .document(id)
            .delete()
            .addOnCompleteListener {
                booksList.removeAt(position)
                Toast.makeText(context, "Book has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }
}