package ru.nsu.databases.ui.animals.list

import ru.nsu.databases.domain.model.zoo.Animal

class AnimalWrapper(
    val animal: Animal,
    val warmNeeded: Boolean,
    val incompatibleNeighbour: Animal? = null,
)