package ru.nsu.databases.ui.animals.details

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor() : BaseViewModel() {

    private lateinit var animal: Animal

    fun setAnimal(animal: Animal) {
        this.animal = animal
        refreshAnimal()
    }

    private fun refreshAnimal() {

    }

    fun onSaveChanges(initialAnimal: Animal) {

    }

    fun onExchangeAnimal(initialAnimal: Animal) {

    }

    fun onEdit() {

    }

}