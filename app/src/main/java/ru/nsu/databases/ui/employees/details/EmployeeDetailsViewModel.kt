package ru.nsu.databases.ui.employees.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val employeeDao: EmployeeDao,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<Unit>()
    val navEvent: LiveData<Unit> = _navEvent

    private val _isEdit = MutableLiveData(false)
    val isEdit: LiveData<Boolean> = _isEdit

    fun onEdit() {

    }

    fun onFireEmployee(employee: Employee) {

    }

    fun onSaveChanges(employee: Employee) {

    }

}