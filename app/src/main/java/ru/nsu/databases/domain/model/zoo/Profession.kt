package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Profession(
    val id: Int,
    val name: String,
) : Parcelable