package ru.nsu.databases.domain.model.zoo

import java.util.Date

class AnimalSettling (
    val animalId: Int,
    val aviaryId: Int,
    val settlingStartDate: Date,
    val settlingEndDate: Date?
)