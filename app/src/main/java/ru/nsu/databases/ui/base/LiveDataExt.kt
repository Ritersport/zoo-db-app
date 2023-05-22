package ru.nsu.databases.ui.base

import androidx.lifecycle.MutableLiveData

inline fun <T> MutableLiveData<T>.update(crossinline updater: (T?) -> T?) {
    val newValue = updater(this.value)
    this.value = newValue
}