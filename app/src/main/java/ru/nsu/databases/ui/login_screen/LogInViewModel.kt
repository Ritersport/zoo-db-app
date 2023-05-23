package ru.nsu.databases.ui.login_screen

import android.util.Log
import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import oracle.net.aso.C03.e
import ru.nsu.databases.data.repository.database.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.Credentials
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
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
            .subscribe(
                { connectionOpeningResult ->
                    Log.e("LogInViewModel", "Connecton opening result $connectionOpeningResult")
                },
                { error ->
                    Log.e("LogInViewModel", e.toString())
                }
            ).unsubscribeOnCleared()
//        _navEvent.update { LoginFragmentRoutes.ToMain }
    }

}