package ru.nsu.databases.ui.animals.list

import ru.nsu.databases.domain.model.zoo.Animal

sealed interface AnimalsListDirections {

    object ToAddAnimal : AnimalsListDirections
    class ToAnimalDetails(val animal: Animal) : AnimalsListDirections
}