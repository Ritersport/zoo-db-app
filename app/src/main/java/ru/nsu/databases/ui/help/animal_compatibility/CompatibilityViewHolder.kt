package ru.nsu.databases.ui.help.animal_compatibility

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.CompatibilityItemBinding
import ru.nsu.databases.domain.model.zoo.IncompatibleSpecies

class CompatibilityViewHolder(
    private val binding: CompatibilityItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(incompatibleSpecies: IncompatibleSpecies) = binding.run {
        name1.text = incompatibleSpecies.specie1.name
        name2.text = incompatibleSpecies.specie2.name
    }
}