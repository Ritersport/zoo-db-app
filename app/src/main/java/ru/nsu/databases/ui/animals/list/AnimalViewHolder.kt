package ru.nsu.databases.ui.animals.list

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.AnimalShortItemBinding
import ru.nsu.databases.domain.model.zoo.Animal

class AnimalViewHolder(
    private val binding: AnimalShortItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animal: AnimalMigrationWrapper, onItemClicked: (Animal) -> Unit) =
        binding.run {
            name.text = animal.animal.name
            kind.text = animal.animal.kind.name
            binding.setWarning(animal.migrationNeeded)

            binding.root.setOnClickListener { onItemClicked(animal.animal) }
        }

    fun AnimalShortItemBinding.setWarning(warn: Boolean) = if (warn) {

    } else {

    }
}