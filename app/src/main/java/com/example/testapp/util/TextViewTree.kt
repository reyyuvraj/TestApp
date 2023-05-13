package com.example.testapp.util

import android.widget.TextView
import timber.log.Timber

class TextViewTree(private val textView: TextView) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val formattedMessage = "$message\n"
        textView.append(formattedMessage)
    }
}