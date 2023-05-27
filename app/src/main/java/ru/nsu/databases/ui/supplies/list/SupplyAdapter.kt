package ru.nsu.databases.ui.supplies.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.SupplyShortItemBinding
import ru.nsu.databases.domain.model.zoo.FeedSupply

class SupplyAdapter : RecyclerView.Adapter<SupplyViewHolder>() {

    var items: List<FeedSupply> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplyViewHolder =
        SupplyViewHolder(
            SupplyShortItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SupplyViewHolder, position: Int) =
        holder.bind(items[position])

}