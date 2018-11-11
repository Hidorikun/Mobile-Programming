package com.example.george.firstapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    init {}

    class BookViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
      holder.view.name.text = BookRepository.books[position].title

        holder.view.setOnClickListener {
            val id = BookRepository.books[position].id
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra("id", id)
            }

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = BookRepository.books.size
}