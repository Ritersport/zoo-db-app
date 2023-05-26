package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Animal(
    val id: Int,
    val kind: Specie,
    val name: String,
    val gender: Gender,
    val birthDate: Date?,
    val father: AnimalParent?,
    val mother: AnimalParent?
) : Parcelable