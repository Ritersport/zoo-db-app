package ru.nsu.databases.ui.employees.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.ui.base.view.BaseViewModel
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val employeeDao: EmployeeDao,
) : BaseViewModel() {

    private val _employees: MutableLiveData<List<Employee>> = MutableLiveData()
    val employees: LiveData<List<Employee>> = _employees

    private val _navEvent = SingleLiveEvent<EmployeesListDirections>()
    val navEvent: LiveData<EmployeesListDirections> = _navEvent


    init {
        loadEmployeeList()
    }

    private fun loadEmployeeList() {
        employeeDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onEmployeesResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onEmployeesResult(employees: List<Employee>) = _employees.update { employees }

    fun onAddEmployee() =
        _navEvent.update { EmployeesListDirections.ToAddEmployee }

    fun onEmployeeClicked(employee: Employee) =
        _navEvent.update { EmployeesListDirections.ToEmployeeDetails(employee) }
}