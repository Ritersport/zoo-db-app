package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Animal(
    val id: Int,
    val kind: Specie,
    val name: String,
    val gender: Gender,
    val birthDate: Date?,
    val father: AnimalParent?,
    val mother: AnimalParent?,
) : Parcelable {

    val ageMonth: Int?
        get() = birthDate?.time?.let {
            ((Date().time - it) / (1000L * 60 * 60 * 24 * 30)).toInt()
        }
}