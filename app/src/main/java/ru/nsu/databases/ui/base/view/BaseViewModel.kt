@file:Suppress("NOTHING_TO_INLINE")

package ru.nsu.databases.ui.base.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update

open class BaseViewModel : ViewModel() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    @Suppress("PropertyName")
    protected val _showErrorDialog = SingleLiveEvent<Throwable>()
    val showErrorDialog: LiveData<Throwable> = _showErrorDialog

    @Suppress("PropertyName")
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected inline fun <T> Single<T>.setupDefaultSchedulers(): Single<T> =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    protected inline fun Completable.setupDefaultSchedulers(): Completable =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    protected inline fun Completable.bindLoading(): Completable =
        this.doOnComplete {
            _isLoading.update { false }
        }.doOnError {
            _isLoading.update { false }
        }.doOnSubscribe {
            _isLoading.update { true }
        }

    protected inline fun <T> Single<T>.bindLoading(): Single<T> =
        this.doOnSuccess {
            _isLoading.update { false }
        }.doOnError {
            _isLoading.update { false }
        }.doOnSubscribe {
            _isLoading.update { true }
        }

    protected fun Disposable.unsubscribeOnCleared() {
        subscriptions.add(this)
    }

    protected fun onError(e: Throwable) {
        _isLoading.update { false }
        _showErrorDialog.update { e }
        Log.e("BaseViewModelHandler", e.stackTraceToString())
    }

    fun onDismiss() = subscriptions.clear()
}