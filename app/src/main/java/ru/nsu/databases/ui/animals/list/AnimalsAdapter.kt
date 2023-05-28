package ru.nsu.databases.ui.animals.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.AnimalShortItemBinding
import ru.nsu.databases.databinding.FilterItemBinding
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.ui.base.view.FilterViewHolder

class AnimalsAdapter(
    private val onItemClicked: (Animal) -> Unit,
    private val onFilterClicked: () -> Unit = {},
    private val onClearFilter: () -> Unit = {},
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<AnimalMigrationWrapper> = emptyList()
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
            AnimalViewHolder(
                AnimalShortItemBinding.inflate(
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
            (holder as? AnimalViewHolder)?.bind(items[position - 1], onItemClicked)
        }
    }


}