package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Specie(
    val id: Int,
    val name: String,
    val nutritionType: NutritionType,
    //'y' or 'n'
    val warmAviary: String,
    val reproductiveAgeMinMonth: Int,
    val reproductiveAgeMaxMonth: Int?,
) : Parcelable {

    override fun toString(): String = name
}