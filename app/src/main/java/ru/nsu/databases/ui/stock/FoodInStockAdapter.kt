package ru.nsu.databases.ui.stock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.FoodInStockItemBinding
import ru.nsu.databases.domain.model.zoo.FoodInStock

class FoodInStockAdapter : RecyclerView.Adapter<FoodInStockViewHolder>() {

    var items: List<FoodInStock> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInStockViewHolder =
        FoodInStockViewHolder(
            FoodInStockItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FoodInStockViewHolder, position: Int) =
        holder.bind(items[position])

}