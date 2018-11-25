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
                    book.description = txt_description.text.toString()
                    book.title = txt_name.text.toString()
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

    private fun getNewId(): Number {
        val id: Number? = realm.where<Book>().max("id")

        if (id != null) {
            return id.toInt() + 1
        }

        return 0
    }

}