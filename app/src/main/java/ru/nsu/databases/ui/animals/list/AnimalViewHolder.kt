package ru.nsu.databases.ui.animals.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.AnimalShortItemBinding
import ru.nsu.databases.domain.model.zoo.Animal

class AnimalViewHolder(
    private val binding: AnimalShortItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animal: AnimalWrapper, onItemClicked: (Animal) -> Unit) =
        binding.run {
            name.text = animal.animal.name
            kind.text = animal.animal.kind.name
            gender.text = animal.animal.gender.name
            setWarmWarning(animal.warmNeeded)
            setIncompatibleNeighbourWarning(animal.incompatibleNeighbour)
            binding.root.setOnClickListener { onItemClicked(animal.animal) }
        }

    private fun AnimalShortItemBinding.setIncompatibleNeighbourWarning(neighbour: Animal?) =
        if (neighbour == null) {
            this.moveWarning.visibility = View.GONE
        } else {
            this.moveWarning.visibility = View.VISIBLE
            this.moveWarning.text =
                "Move needed due to incompatible neighbour: ${neighbour.name}, ${neighbour.kind.name}"
        }


    private fun AnimalShortItemBinding.setWarmWarning(warn: Boolean) = if (warn) {
        this.warmWarning.visibility = View.VISIBLE
    } else {
        this.warmWarning.visibility = View.GONE
    }
}