package ru.nsu.databases.ui.animals.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.ui.base.BaseViewModel
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.update
import ru.nsu.databases.ui.employees.list.EmployeesListDirections
import javax.inject.Inject

@HiltViewModel
class AnimalsListViewModel @Inject constructor(
    private val animalsDao: AnimalsDao,
) : BaseViewModel() {

    private val _animals: MutableLiveData<List<AnimalMigrationWrapper>> = MutableLiveData()
    val animals: LiveData<List<AnimalMigrationWrapper>> = _animals

    private val _navEvent = SingleLiveEvent<AnimalsListDirections>()
    val navEvent: LiveData<AnimalsListDirections> = _navEvent

    init {
        loadAnimalsList()
    }

    private fun loadAnimalsList() {
        animalsDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onAnimalsResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onAnimalsResult(animals: List<Animal>) = _animals.update { animals.map { AnimalMigrationWrapper(it, false) } }

    fun onAnimalClicked(animal: Animal) {

    }
}