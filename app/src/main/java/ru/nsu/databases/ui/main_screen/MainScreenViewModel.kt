package ru.nsu.databases.ui.main_screen

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val credentialsStorage: CredentialsStorage,
) : BaseViewModel() {

    private val _navigateToLogin = SingleLiveEvent<Unit>()
    val navigateToLogin: LiveData<Unit> = _navigateToLogin

    fun onLogout() {
        credentialsStorage.clearCredentials()
            .setupDefaultSchedulers()
            .subscribe(
                ::onCredentialsCleared,
                ::onError
            ).unsubscribeOnCleared()
    }

    private fun onCredentialsCleared() = _navigateToLogin.update { Unit }
}