package ru.nsu.databases.ui.animals.details

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val animalsDao: AnimalsDao,
) : BaseViewModel() {

    private val _navEvent = SingleLiveEvent<AnimalDetailsFragmentDirections>()
    val navEvent: LiveData<AnimalDetailsFragmentDirections> = _navEvent

    fun onSaveChanges(initialAnimal: Animal) {

    }

    fun onExchangeAnimal(initialAnimal: Animal) {

    }

    fun onEdit() {

    }

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

}