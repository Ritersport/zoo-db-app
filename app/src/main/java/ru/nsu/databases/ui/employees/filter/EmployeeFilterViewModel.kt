package ru.nsu.databases.ui.employees.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDao
import ru.nsu.databases.domain.model.zoo.Profession
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeFilterViewModel @Inject constructor(
    private val professionsDao: ProfessionsDao,
) : BaseViewModel() {

    private val _professions: MutableLiveData<List<Profession>> = MutableLiveData()
    val professions: LiveData<List<Profession>> = _professions

    var profession: Profession? = null

    init {
        loadProfessions()
    }

    private fun loadProfessions() {
        professionsDao.getAll().setupDefaultSchedulers().subscribe(
            ::onProfessions,
            ::onError,
        ).unsubscribeOnCleared()
    }

    private fun onProfessions(professions: List<Profession>) = _professions.update { professions }

    fun onProfessionSelected(item: Profession?) {
        profession = item
    }

    fun onClearFilters() {
        profession = null
    }
}