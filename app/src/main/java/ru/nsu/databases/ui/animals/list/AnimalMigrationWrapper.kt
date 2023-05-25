package ru.nsu.databases.ui.animals.list

import ru.nsu.databases.domain.model.zoo.Animal

class AnimalMigrationWrapper(
    val animal: Animal,
    val migrationNeeded: Boolean
)