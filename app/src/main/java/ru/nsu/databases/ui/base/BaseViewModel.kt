package ru.nsu.databases.ui.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected fun Disposable.unsubscribeOnCleared() {
        subscriptions.add(this)
    }
}