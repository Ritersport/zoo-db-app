package ru.nsu.databases.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private val _showErrorDialog = SingleLiveEvent<Throwable>()
    val showErrorDialog: LiveData<Throwable> = _showErrorDialog

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> Single<T>.setupDefaultSchedulers(): Single<T> =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> Observable<T>.setupDefaultSchedulers(): Observable<T> =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> Maybe<T>.setupDefaultSchedulers(): Maybe<T> =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun Completable.setupDefaultSchedulers(): Completable =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    protected fun Disposable.unsubscribeOnCleared() {
        subscriptions.add(this)
    }

    protected fun onError(e: Throwable) {
        Log.e("BaseViewModelHandler", e.stackTraceToString())
        _showErrorDialog.update { e }
    }
}