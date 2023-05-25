package ru.nsu.databases.ui.animals.list

import ru.nsu.databases.domain.model.zoo.Animal

sealed interface AnimalsListDirections {

    object ToAddEmployee : AnimalsListDirections
    class ToEmployeeDetails(val animal: Animal) : AnimalsListDirections
}