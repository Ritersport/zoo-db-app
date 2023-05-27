package ru.nsu.databases.ui.animals.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
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
            .zipWith(animalsDao.getWarmCageNeededAnimalIds()) { animals, ids ->
                animals.map {
                    AnimalMigrationWrapper(
                        animal = it,
                        migrationNeeded = (it.id in ids)
                    )
                }
            }
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onAnimalsResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onAnimalsResult(animals: List<AnimalMigrationWrapper>) =
        _animals.update { animals }

    fun onAnimalClicked(animal: Animal) = _navEvent.update {
        AnimalsListDirections.ToAnimalDetails(animal)
    }
}