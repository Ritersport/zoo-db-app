package ru.nsu.databases.ui.animals.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.ui.animals.filter.AnimalFilter
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalsListViewModel @Inject constructor(
    private val animalsDao: AnimalsDao,
) : BaseViewModel() {

    private val _animals: MutableLiveData<List<AnimalWrapper>> = MutableLiveData()
    val animals: LiveData<List<AnimalWrapper>> = _animals

    private val _navEvent = SingleLiveEvent<AnimalsListDirections>()
    val navEvent: LiveData<AnimalsListDirections> = _navEvent

    private var allAnimals: List<AnimalWrapper>? = null

    init {
        loadAnimalsList()
    }

    private fun loadAnimalsList() {
        Single.zip(
            animalsDao.getAll(),
            animalsDao.getWarmCageNeededAnimalIds(),
            animalsDao.getMoveNeeded(),
        ) { animals, ids, move ->
            animals.map { animal ->
                AnimalWrapper(
                    animal = animal,
                    warmNeeded = (animal.id in ids),
                    incompatibleNeighbour = move.find { it.first.id == animal.id }?.second
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

    private fun onAnimalsResult(animals: List<AnimalWrapper>) =
        _animals.update {
            allAnimals = animals
            animals
        }

    fun onAnimalClicked(animal: Animal) = _navEvent.update {
        AnimalsListDirections.ToAnimalDetails(animal)
    }

    fun setAnimalFilter(filter: AnimalFilter) = _animals.update {
        allAnimals
            ?.filterByKind(filter.kind)
            ?.filterByMaxAge(filter.maxAgeMonth)
            ?.filterByMinAge(filter.minAgeMonth)
            ?.filterByGender(filter.gender)
            ?.filterByNutritionType(filter.nutritionType)
    }

    fun onFilterClicked() = _navEvent.update { AnimalsListDirections.ToFilter }

    fun onClearFilter() = setAnimalFilter(AnimalFilter.Empty)

    private fun List<AnimalWrapper>.filterByKind(value: Specie?): List<AnimalWrapper> {
        if (value == null) return this
        return this.filter { it.animal.kind.id == value.id }
    }

    private fun List<AnimalWrapper>.filterByMaxAge(value: Int?): List<AnimalWrapper> {
        if (value == null) return this
        return this.filter {
            if (it.animal.ageMonth == null) {
                false
            } else {
                it.animal.ageMonth!! <= value
            }
        }
    }

    private fun List<AnimalWrapper>.filterByMinAge(value: Int?): List<AnimalWrapper> {
        if (value == null) return this
        return this.filter {
            if (it.animal.ageMonth == null) {
                false
            } else {
                it.animal.ageMonth!! >= value
            }
        }
    }

    private fun List<AnimalWrapper>.filterByGender(value: Gender?): List<AnimalWrapper> {
        if (value == null) return this
        return this.filter { it.animal.gender.name == value.name }
    }

    private fun List<AnimalWrapper>.filterByNutritionType(value: NutritionType?): List<AnimalWrapper> {
        if (value == null) return this
        return this.filter { it.animal.kind.nutritionType.id == value.id }
    }

    fun refreshAnimals() {
        Single.zip(
            animalsDao.getAll(),
            animalsDao.getWarmCageNeededAnimalIds(),
            animalsDao.getMoveNeeded(),
        ) { animals, ids, move ->
            animals.map { animal ->
                AnimalWrapper(
                    animal = animal,
                    warmNeeded = (animal.id in ids),
                    incompatibleNeighbour = move.find { it.first.id == animal.id }?.second
                )
            }
        }
            .setupDefaultSchedulers()
            .subscribe(
                ::onAnimalsResult,
                ::onError,
            ).unsubscribeOnCleared()
    }
}