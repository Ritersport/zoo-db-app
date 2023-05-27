package ru.nsu.databases.ui.splash_screen

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import java.util.concurrent.TimeUnit
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
        credentialsStorage.hasCredentials()
            .delay(700, TimeUnit.MILLISECONDS)
            .setupDefaultSchedulers()
            .subscribe { loggedIn ->
                if (loggedIn) {
                    _navEvent.update { SplashRoutes.ToMainScreen }
                } else {
                    _navEvent.update { SplashRoutes.ToLogin }
                }
            }.unsubscribeOnCleared()
    }
}