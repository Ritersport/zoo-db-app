package ru.nsu.databases.ui.splash_screen

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.update
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val credentialsStorage: CredentialsStorage,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<SplashRoutes>()
    val navEvent: LiveData<SplashRoutes> = _navEvent

    init {
        checkCredentials()
    }

    private fun checkCredentials() {
        credentialsStorage.isLoggedIn()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loggedIn ->
                if (loggedIn) {
                    _navEvent.update { SplashRoutes.ToMainScreen }
                } else {
                    _navEvent.update { SplashRoutes.ToLogin }
                }
            }.unsubscribeOnCleared()
    }
}