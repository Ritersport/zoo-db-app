package ru.nsu.databases.ui.employees.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.nsu.databases.domain.model.zoo.Profession

@Parcelize
class EmployeeFilter(
    val maxSalary: Int?,
    val minSalary: Int?,
    val profession: Profession?,
    val showFired: Boolean = false,
) : Parcelable {

    companion object {

        val Empty = EmployeeFilter(null, null, null)
    }
}