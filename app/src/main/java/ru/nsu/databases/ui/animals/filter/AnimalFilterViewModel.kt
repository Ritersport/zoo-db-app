package ru.nsu.databases.ui.animals.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.genders.GendersDao
import ru.nsu.databases.data.repository.database.daos.nutrition_type.NutritionTypeDao
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalFilterViewModel @Inject constructor(
    private val gendersDao: GendersDao,
    private val speciesDao: SpeciesDao,
    private val nutritionTypeDao: NutritionTypeDao,
) : BaseViewModel() {

    private val _genders: MutableLiveData<List<Gender>> = MutableLiveData()
    val genders: LiveData<List<Gender>> = _genders

    private val _species: MutableLiveData<List<Specie>> = MutableLiveData()
    val species: LiveData<List<Specie>> = _species

    private val _nutritionTypes: MutableLiveData<List<NutritionType>> = MutableLiveData()
    val nutritionTypes: LiveData<List<NutritionType>> = _nutritionTypes

    var gender: Gender? = null
    var nutritionType: NutritionType? = null
    var specie: Specie? = null

    init {
        loadSpecies()
        loadGenders()
        loadNutritionTypes()
    }

    fun onNutritionTypeSelected(item: NutritionType?) {
        nutritionType = item
    }

    fun onGenderSelected(item: Gender?) {
        gender = item
    }

    fun onSpecieSelected(item: Specie?) {
        specie = item
    }

    private fun loadNutritionTypes() {
        nutritionTypeDao.getAll().setupDefaultSchedulers().subscribe(
            ::onNutritionTypes,
            ::onError,
        ).unsubscribeOnCleared()
    }

    private fun loadGenders() {
        gendersDao.getAll().setupDefaultSchedulers().subscribe(
            ::onGenders,
            ::onError,
        ).unsubscribeOnCleared()
    }


    private fun loadSpecies() {
        speciesDao.getAll().setupDefaultSchedulers().subscribe(
            ::onSpecies,
            ::onError,
        ).unsubscribeOnCleared()
    }

    private fun onNutritionTypes(value: List<NutritionType>) = _nutritionTypes.update { value }

    private fun onGenders(value: List<Gender>) = _genders.update { value }

    private fun onSpecies(value: List<Specie>) = _species.update { value }

    fun onClearFilters() {
        gender = null
        nutritionType = null
        specie = null
    }
}