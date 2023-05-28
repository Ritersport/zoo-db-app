package ru.nsu.databases.ui.employees.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.domain.model.zoo.Profession
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import ru.nsu.databases.ui.employees.filter.EmployeeFilter
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val employeeDao: EmployeeDao,
) : BaseViewModel() {

    private val _employees: MutableLiveData<List<Employee>> = MutableLiveData()
    val employees: LiveData<List<Employee>> = _employees

    private val _navEvent = SingleLiveEvent<EmployeesListDirections>()
    val navEvent: LiveData<EmployeesListDirections> = _navEvent

    private var allEmployees: List<Employee>? = null
    private var employeeFilter: EmployeeFilter = EmployeeFilter.Empty

    init {
        loadEmployeeList()
    }

    private fun loadEmployeeList() {
        employeeDao.getAll()
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onEmployeesResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    fun refreshEmployeeList() {
        employeeDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onEmployeesResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onEmployeesResult(employees: List<Employee>) {
        allEmployees = employees
        setEmployeeFilter(employeeFilter)
    }

    fun onFilterClicked() = _navEvent.update { EmployeesListDirections.ToFilter }

    fun onClearFilter() = setEmployeeFilter(EmployeeFilter.Empty)

    fun setEmployeeFilter(filter: EmployeeFilter) = _employees.update {
        employeeFilter = filter
        allEmployees
            ?.filterByIsFired(filter.showFired)
            ?.filterByMaxSalary(filter.maxSalary)
            ?.filterByMinSalary(filter.minSalary)
            ?.filterByProfession(filter.profession)
    }

    fun onAddEmployee() =
        _navEvent.update { EmployeesListDirections.ToAddEmployee }

    fun onEmployeeClicked(employee: Employee) =
        _navEvent.update { EmployeesListDirections.ToEmployeeDetails(employee) }

    private fun List<Employee>.filterByProfession(value: Profession?): List<Employee> {
        if (value == null) return this
        return this.filter { it.profession.id == value.id }
    }

    private fun List<Employee>.filterByMaxSalary(value: Int?): List<Employee> {
        if (value == null) return this
        return this.filter { it.salary <= value }
    }

    private fun List<Employee>.filterByMinSalary(value: Int?): List<Employee> {
        if (value == null) return this
        return this.filter { it.salary >= value }
    }

    private fun List<Employee>.filterByIsFired(value: Boolean): List<Employee> =
        if (value) {
            this.filter { it.dismissalDate != null }
        } else {
            this.filter { it.dismissalDate == null }
        }
}