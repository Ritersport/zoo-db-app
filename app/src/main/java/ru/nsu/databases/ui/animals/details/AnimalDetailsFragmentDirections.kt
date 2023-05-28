package ru.nsu.databases.ui.animals.details

import ru.nsu.databases.domain.model.zoo.Animal

sealed interface AnimalDetailsFragmentDirections {

    class ToParent(
        val parent: Animal,
    ): AnimalDetailsFragmentDirections
}