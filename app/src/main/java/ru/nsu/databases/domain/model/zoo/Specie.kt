package ru.nsu.databases.domain.model.zoo

class Specie(
    val id: Int,
    val name: String,
    val nutritionType: NutritionType,
    //'y' or 'n'
    val warmAviary: String,
    val reproductiveAgeMinMonth: Int,
    val reproductiveAgeMaxMonth: Int?,
)