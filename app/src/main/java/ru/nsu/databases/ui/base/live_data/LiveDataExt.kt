package ru.nsu.databases.ui.base.live_data

import androidx.lifecycle.MutableLiveData

inline fun <T> MutableLiveData<T>.update(crossinline updater: (T?) -> T?) {
    val newValue = updater(this.value)
    this.value = newValue
}

@Suppress("NOTHING_TO_INLINE")
inline fun MutableLiveData<Unit>.push() {
    this.value = Unit
}