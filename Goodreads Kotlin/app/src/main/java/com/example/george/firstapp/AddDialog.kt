package com.example.george.firstapp

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.Window
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.add_book.*


class AddDialog(private val activity: Activity) : Dialog(activity), View.OnClickListener {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.add_book)

        btn_add.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)

        realm = Realm.getDefaultInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                realm.executeTransaction { realm ->
                    val book = realm.createObject<Book>(getNewId())
                    book.description = txt_author.text.toString()
                    book.title = txt_name.text.toString()
                    book.rating = rating_edit.text.toString().toInt()
                    book.author = txt_author.text.toString()
                }
                Snackbar.make(v, "Added", Snackbar.LENGTH_LONG)
                dismiss()
            }

            R.id.btn_cancel -> {
                Snackbar.make(v, "Cancel", Snackbar.LENGTH_LONG)
                dismiss()
            }
        }
    }

    private fun getNewId(): String {
        val books = realm.where<Book>().findAll()

        var id = 0

        for (book in books) {
            try {
                var k = book.id?.toInt()
                if (k != null && k > id) {
                    id = k
                }
            } catch (e: Exception) {

            }
        }

        return (id + 1).toString()
    }

}