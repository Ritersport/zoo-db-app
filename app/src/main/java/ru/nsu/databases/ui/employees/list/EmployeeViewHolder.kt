package ru.nsu.databases.ui.employees.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.EmployeeShortItemBinding
import ru.nsu.databases.domain.model.zoo.Employee

class EmployeeViewHolder(
    private val binding: EmployeeShortItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(employee: Employee, onClicked: (Employee) -> Unit) = binding.run {
        nameSurname.text = "${employee.surname} ${employee.name}"
        profession.text = "Profession: ${employee.profession.name}"
        salary.text = "${employee.salary} P"

        root.setOnClickListener { onClicked(employee) }
    }
}