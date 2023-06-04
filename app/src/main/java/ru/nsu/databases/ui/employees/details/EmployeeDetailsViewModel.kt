package ru.nsu.databases.ui.employees.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.data.repository.database.daos.genders.GendersDao
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDao
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.Profession
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.push
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val employeeDao: EmployeeDao,
    private val gendersDao: GendersDao,
    private val professionsDao: ProfessionsDao,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<Unit>()
    val navEvent: LiveData<Unit> = _navEvent

    private val _screenState: MutableLiveData<DetailsState> = MutableLiveData()
    val screenState: LiveData<DetailsState> = _screenState

    private val _employee: MutableLiveData<Employee> = MutableLiveData()
    val employee: LiveData<Employee> = _employee

    private val _professions: MutableLiveData<List<Profession>> = MutableLiveData()
    val professions: LiveData<List<Profession>> = _professions

    private val _genders: MutableLiveData<List<Gender>> = MutableLiveData()
    val genders: LiveData<List<Gender>> = _genders

    var selectedProfession: Profession? = null
    var selectedGender: Gender? = null

    init {
        loadProfessions()
        loadGenders()
    }

    private fun loadGenders() {
        gendersDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onGenders,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun loadProfessions() {
        professionsDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onProfessions,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onGenders(genders: List<Gender>) = _genders.update { genders }

    private fun onProfessions(professions: List<Profession>) = _professions.update { professions }

    fun setEditState() = _screenState.update { DetailsState.Edit }

    fun setViewState() = _screenState.update { DetailsState.View }

    fun setAddState() = _screenState.update { DetailsState.Add }

    fun onFireEmployee() = _employee.value?.let { employee ->
        val date = Date()
        employeeDao.fireEmployeeById(employee.id, date)
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onEmployeeFired,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onEmployeeFired() = _navEvent.push()

    fun onSaveChanges(employee: Employee) {
        when (screenState.value) {
            DetailsState.Add -> {

                employeeDao.add(employee)
                    .setupDefaultSchedulers()
                    .bindLoading()
                    .subscribe(
                        ::onEmployeeSaved,
                        ::onError,
                    ).unsubscribeOnCleared()
            }

            DetailsState.Edit -> {
                employeeDao.update(employee)
                    .setupDefaultSchedulers()
                    .bindLoading()
                    .subscribe(
                        ::onEmployeeSaved,
                        ::onError,
                    ).unsubscribeOnCleared()
            }

            else -> {}
        }
    }

    private fun onEmployeeSaved() = _screenState.update { DetailsState.View }

    fun setEmployee(employee: Employee?) = if (employee == null) {
        _screenState.update { DetailsState.Add }
    } else {
        _screenState.update { DetailsState.View }
        _employee.update { employee }
    }

    fun onProfessionSelected(profession: Profession?) {
        selectedProfession = profession
    }

    fun onGenderSelected(gender: Gender?) {
        selectedGender = gender
    }

    fun onDelete() = _employee.value?.let {
        employeeDao.removeById(it.id)
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onDeleted,
                ::onError,
            )
            .unsubscribeOnCleared()
    }

    private fun onDeleted() = _navEvent.push()
}