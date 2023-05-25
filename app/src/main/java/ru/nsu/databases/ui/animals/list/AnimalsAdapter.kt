package ru.nsu.databases.ui.animals.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.AnimalShortItemBinding
import ru.nsu.databases.domain.model.zoo.Animal

class AnimalsAdapter(
    private val onItemClicked: (Animal) -> Unit
) : RecyclerView.Adapter<AnimalViewHolder>() {

    var items: List<AnimalMigrationWrapper> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder =
        AnimalViewHolder(
            AnimalShortItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) =
        holder.bind(items[position], onItemClicked)

}