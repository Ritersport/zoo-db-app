package ru.nsu.databases.ui.login_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(

) : ViewModel() {

    private val _navEvent = SingleLiveEvent<LoginFragmentRoutes>()
    val navEvent: LiveData<LoginFragmentRoutes> = _navEvent

    fun onSubmit(login: String, password: String) {
        _navEvent.update { LoginFragmentRoutes.ToMain }
    }
}