package ru.nsu.databases.ui.animals.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie

@Parcelize
class AnimalFilter(
    val kind: Specie?,
    val minAgeMonth: Int?,
    val maxAgeMonth: Int?,
    val gender: Gender?,
    val nutritionType: NutritionType?,
) : Parcelable {

    companion object {

        val Empty = AnimalFilter(null, null, null, null, null)
    }
}