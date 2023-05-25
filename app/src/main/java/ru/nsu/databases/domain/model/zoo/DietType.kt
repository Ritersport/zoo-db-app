package ru.nsu.databases.domain.model.zoo

class DietType (
    val id: Int,
    val kind: Int,
    val minAgeMonth: Int,
    val maxAgeMonth: Int?,
    val seasonStart: Int,
    val seasonEnd: Int,
        )