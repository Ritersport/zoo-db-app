package ru.nsu.databases.ui.animals.list

import android.view.View
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
            gender.text = animal.animal.gender.name
            binding.setWarning(animal.migrationNeeded)

            binding.root.setOnClickListener { onItemClicked(animal.animal) }
        }

    private fun AnimalShortItemBinding.setWarning(warn: Boolean) = if (warn) {
        this.warning.visibility = View.VISIBLE
    } else {
        this.warning.visibility = View.GONE
    }

}