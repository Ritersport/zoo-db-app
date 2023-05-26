package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AnimalParent(
    val id: Int,
    val name: String,
    val specieId: Int,
):Parcelable