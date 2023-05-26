package ru.nsu.databases.ui.splash_screen

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.view.BaseViewModel
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val credentialsStorage: CredentialsStorage,
    private val connectionProvider: DatabaseConnectionProvider,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<SplashRoutes>()
    val navEvent: LiveData<SplashRoutes> = _navEvent

    init {
        checkCredentials()
    }

    private fun checkCredentials() {
        credentialsStorage.hasCredentials()
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