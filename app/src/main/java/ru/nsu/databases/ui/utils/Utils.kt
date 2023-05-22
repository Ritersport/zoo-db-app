package ru.nsu.databases.ui.utils

import com.google.android.material.textfield.TextInputEditText


fun TextInputEditText.stringOrEmpty(): String {
    val text = this.text ?: ""
    return text.toString()
}