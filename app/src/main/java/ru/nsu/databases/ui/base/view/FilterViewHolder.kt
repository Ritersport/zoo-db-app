package ru.nsu.databases.ui.base.view

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.databases.databinding.FilterItemBinding

class FilterViewHolder(
    binding: FilterItemBinding,
    onClick: () -> Unit,
    onClearFilter: () -> Unit = {},
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { onClick() }
        binding.clearButton.setOnClickListener { onClearFilter() }
    }
}