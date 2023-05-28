package ru.nsu.databases.ui.employees.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.EmployeeShortItemBinding
import ru.nsu.databases.databinding.FilterItemBinding
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.ui.base.view.FilterViewHolder

class EmployeeAdapter(
    private val onItemClicked: (Employee) -> Unit,
    private val onFilterClicked: () -> Unit = {},
    private val onClearFilter: () -> Unit = {},
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Employee> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0) {
            FilterViewHolder(
                FilterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onFilterClicked,
                onClearFilter,
            )
        } else {
            EmployeeViewHolder(
                EmployeeShortItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        0
    } else {
        1
    }

    override fun getItemCount(): Int = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != 0) {
            (holder as? EmployeeViewHolder)?.bind(items[position - 1], onItemClicked)
        }
    }

}