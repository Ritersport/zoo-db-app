package ru.nsu.databases.ui.login_screen

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.security.Credentials
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
    private val credentialsStorage: CredentialsStorage,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<LoginFragmentRoutes>()
    val navEvent: LiveData<LoginFragmentRoutes> = _navEvent

    fun onSubmit(login: String, password: String) {
        connectionProvider.checkConnection(Credentials(login, password))
            .setupDefaultSchedulers()
            .subscribe(
                ::onConnectSuccess,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onConnectSuccess(credentials: Credentials) {
        credentialsStorage.saveCredentials(credentials)
            .setupDefaultSchedulers()
            .subscribe(
                { _navEvent.update { LoginFragmentRoutes.ToMain } },
                ::onError
            ).unsubscribeOnCleared()
    }
}