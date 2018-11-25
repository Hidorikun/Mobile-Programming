package com.example.george.firstapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.list_item.view.*

class BookAdapter(var realm: Realm) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        val books = realm.where<Book>().findAll()

        holder.view.name.text = books[position]?.title

        holder.view.btnEdit.setOnClickListener {
            val id = books[position]?.id
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra("id", id)
            }

            it.context.startActivity(intent)
        }

        holder.view.btnDelete.setOnClickListener {
            val id = books[position]?.id

            realm.executeTransaction { realm ->
                val book = realm.where<Book>().equalTo("id", id).findFirst()!!
                book.deleteFromRealm()
            }
        }
    }

    override fun getItemCount() = realm.where<Book>().findAll().size
}