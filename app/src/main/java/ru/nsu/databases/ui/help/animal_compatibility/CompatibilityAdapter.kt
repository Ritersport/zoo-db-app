package ru.nsu.databases.ui.help.animal_compatibility

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.CompatibilityItemBinding
import ru.nsu.databases.domain.model.zoo.IncompatibleSpecies

class CompatibilityAdapter : RecyclerView.Adapter<CompatibilityViewHolder>() {

    var items: List<IncompatibleSpecies> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompatibilityViewHolder =
        CompatibilityViewHolder(
            CompatibilityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CompatibilityViewHolder, position: Int) =
        holder.bind(items[position])
}