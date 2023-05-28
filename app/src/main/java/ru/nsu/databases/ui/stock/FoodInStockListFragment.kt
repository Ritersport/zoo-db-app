package ru.nsu.databases.ui.stock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentFoodInStockListBinding
import ru.nsu.databases.domain.model.zoo.FoodInStock
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class FoodInStockListFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentFoodInStockListBinding.inflate(inflater, container, false)
    }

    override val viewModel: FoodInStockViewModel by viewModels()

    private val foodInStockAdapter by lazy {
        FoodInStockAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foodsList.adapter = foodInStockAdapter
        setupVmObservers()
        setupViewListeners()

        viewModel.refreshStock()
    }

    private fun setupViewListeners() = binding.run {
        addSupply.setOnClickListener {
            viewModel.onAddSupply()
        }
    }

    private fun setupVmObservers() = viewModel.run {
        foodInStock.observe(viewLifecycleOwner, ::onFoodList)
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(direction: FoodInStockFragmentDirections) = when (direction) {
        FoodInStockFragmentDirections.ToAddSupply -> {
            findNavController().navigate(R.id.toAddSupplyFromFoods)
        }
    }

    private fun onFoodList(supplies: List<FoodInStock>) {
        foodInStockAdapter.items = supplies
    }
}