package ru.nsu.databases.ui.help.animal_compatibility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.domain.model.zoo.IncompatibleSpecies
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CompatibilityViewModel @Inject constructor(
    private val speciesDao: SpeciesDao,
) : BaseViewModel() {

    private val _species: MutableLiveData<List<IncompatibleSpecies>> = MutableLiveData()
    val species: LiveData<List<IncompatibleSpecies>> = _species

    init {
        loadIncompatibleSpecies()
    }

    private fun loadIncompatibleSpecies() {
        speciesDao.getIncompatibleSpecies()
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onIncompatibleSpecies,
                ::onError,
            )
            .unsubscribeOnCleared()
    }

    private fun onIncompatibleSpecies(species: List<IncompatibleSpecies>) =
        _species.update { species }
}