package ru.nsu.databases.ui.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
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


inline fun <T : Fragment, reified I> AutoCompleteTextView.setItems(fragment: T, vendors: List<I>) =
    setAdapter(
        ArrayAdapter(
            fragment.requireContext(),
            R.layout.string_adapter_layout,
            vendors.toTypedArray()
        )
    )
