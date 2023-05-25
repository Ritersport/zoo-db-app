package ru.nsu.databases.domain.model.zoo

import java.util.Date

class Animal (
    val id: Int,
    val kind: Int,
    val name: String,
    val gender: Gender,
    val birthdate: Date?,
    val father: Int?,
    val mother: Int?
)