package ru.nsu.databases.ui.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.nsu.databases.R
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

fun Date?.toUiDateSting(): String? =
    this?.let { date -> SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(date) }

inline fun <T : Fragment, reified I> AutoCompleteTextView.setItems(fragment: T, items: List<I>) =
    setAdapter(
        ArrayAdapter(
            fragment.requireContext(),
            R.layout.string_adapter_layout,
            items.toTypedArray()
        )
    )

fun EditText.textOrEmpty(): String = this.text.toString()

fun TextInputLayout.setErrorEnable(error: String) {
    this.error = error
    this.isErrorEnabled = true
}

fun TextInputLayout.clearError() {
    this.error = null
    this.isErrorEnabled = false
}

fun TextView.setTextOrGone(text: String?) {
    if (text == null) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
        this.setText(text)
    }
}