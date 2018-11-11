package com.example.george.firstapp

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.add_book.*

class AddDialog(private val activity: Activity) : Dialog(activity), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.add_book)

        btn_add.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                Snackbar.make(v, "Added", Snackbar.LENGTH_LONG)
                dismiss()
            }

            R.id.btn_cancel -> {
                Snackbar.make(v, "Cancel", Snackbar.LENGTH_LONG)
                dismiss()
            }
        }
    }
}