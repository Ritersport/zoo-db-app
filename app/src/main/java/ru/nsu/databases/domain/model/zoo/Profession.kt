package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profession(
    val id: Int,
    val name: String,
) : Parcelable {

    override fun toString(): String = name
}