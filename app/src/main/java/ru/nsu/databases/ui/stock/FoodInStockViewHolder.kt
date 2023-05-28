package ru.nsu.databases.ui.stock

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.FoodInStockItemBinding
import ru.nsu.databases.domain.model.zoo.FoodInStock

class FoodInStockViewHolder(
    private val binding: FoodInStockItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(food: FoodInStock) =
        binding.run {
            name.text = food.feedType.name
            amount.text = "${food.amount} kg"
        }
}