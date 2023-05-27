package ru.nsu.databases.ui.supplies.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.SupplyShortItemBinding
import ru.nsu.databases.domain.model.zoo.FeedSupply

class SupplyViewHolder(
    private val binding: SupplyShortItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(supply: FeedSupply) =
        binding.run {
            name.text = supply.vendor.feedType.name
            price.text = "${supply.price} rub/kg"
            supplyDate.text = "Date: ${supply.supplyDate}"
            amount.text = "${supply.amount} kg"
            vendor.text = "Vendor: ${supply.vendor.organizationName}"
        }
}