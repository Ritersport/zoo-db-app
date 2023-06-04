package ru.nsu.databases.ui.animals.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.data.repository.database.daos.genders.GendersDao
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val animalsDao: AnimalsDao,
    private val gendersDao: GendersDao,
    private val speciesDao: SpeciesDao,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<AnimalDetailsFragmentDirections>()
    val navEvent: LiveData<AnimalDetailsFragmentDirections> = _navEvent

    private val _screenState: MutableLiveData<DetailsState> = MutableLiveData()
    val screenState: LiveData<DetailsState> = _screenState

    private val _animal: MutableLiveData<Animal> = MutableLiveData()
    val animal: LiveData<Animal> = _animal

    private val _kinds: MutableLiveData<List<Specie>> = MutableLiveData()
    val kinds: LiveData<List<Specie>> = _kinds

    private val _genders: MutableLiveData<List<Gender>> = MutableLiveData()
    val genders: LiveData<List<Gender>> = _genders

    var selectedGender: Gender? = null
    var selectedKind: Specie? = null

    init {
        loadKinds()
        loadGenders()
    }

    private fun loadGenders() {
        gendersDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onAnimalGenders,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onAnimalGenders(genders: List<Gender>) = _genders.update { genders }

    private fun loadKinds() {
        speciesDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onAnimalSpecies,
                ::onError,
            )
            .unsubscribeOnCleared()

    }

    private fun onAnimalSpecies(species: List<Specie>) = _kinds.update { species }

    fun onSaveChanges(initialAnimal: Animal) {
        when (screenState.value) {
            DetailsState.Add -> {
                animalsDao.add(initialAnimal)
                    .setupDefaultSchedulers()
                    .bindLoading()
                    .subscribe(
                        ::onAnimalSaved,
                        ::onError,
                    ).unsubscribeOnCleared()
            }
            DetailsState.Edit -> {
                animalsDao.update(initialAnimal)
                    .setupDefaultSchedulers()
                    .bindLoading()
                    .subscribe(
                        ::onAnimalSaved,
                        ::onError,
                    ).unsubscribeOnCleared()
            }
            else -> {}
        }
    }

    fun onExchangeAnimal(initialAnimal: Animal) {

    }

    fun onEdit() {
        _screenState.update { DetailsState.Edit }
    }

    private fun onAnimalSaved() = _screenState.update { ru.nsu.databases.ui.animals.details.DetailsState.View }


    fun onGoToParent(parent: AnimalParent) {
        animalsDao.getById(parent.id)
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onParentLoaded,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onParentLoaded(animal: Animal) = _navEvent.update {
        AnimalDetailsFragmentDirections.ToParent(animal)
    }

    fun onGenderSelected(gender: Gender?) {
        selectedGender = gender
    }

    fun onKindSelected(kind: Specie?) {
        selectedKind = kind
    }

    fun setAnimal(animal: Animal) = _animal.update { animal }

}