package ru.nsu.databases.ui.utils

import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TextInputEditText.stringOrEmpty(): String {
    val text = this.text ?: ""
    return text.toString()
}

fun TextInputEditText.parseDate(): Date? {
    val dateText = (this.text?.toString() ?: "").trim()
    return SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(dateText)
}
