package ru.nsu.databases.domain.model.zoo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Employee(
    val id: Int,
    val gender: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val birthDate: Date,
    val profession: Profession,
    val salary: Int,
    val dismissalDate: Date?,
    val employmentDate: Date,
) : Parcelable