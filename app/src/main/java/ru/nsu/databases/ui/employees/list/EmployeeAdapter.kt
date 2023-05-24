package ru.nsu.databases.ui.employees.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.EmployeeShortItemBinding
import ru.nsu.databases.domain.model.zoo.Employee

class EmployeeAdapter(
    private val onItemClicked: (Employee) -> Unit
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    var items: List<Employee> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder(
            EmployeeShortItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) =
        holder.bind(items[position], onItemClicked)
}