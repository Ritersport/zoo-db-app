package ru.nsu.databases.ui.supplies.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentSupplyListBinding
import ru.nsu.databases.domain.model.zoo.FeedSupply
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class SupplyListFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentSupplyListBinding.inflate(inflater, container, false)
    }

    override val viewModel: SupplyListViewModel by viewModels()

    private val supplyAdapter by lazy {
        SupplyAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.supplyList.adapter = supplyAdapter
        setupVmObservers()
        setupViewListeners()

        viewModel.refreshSupplies()
    }

    private fun setupViewListeners() = binding.run {
        addSupply.setOnClickListener {
            viewModel.onAddSupply()
        }
    }

    private fun setupVmObservers() = viewModel.run {
        supplies.observe(viewLifecycleOwner, ::onSupppliesList)
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(direction: SupplyFragmentDirections) = when (direction) {
        SupplyFragmentDirections.ToAddSupply -> {
            findNavController().navigate(R.id.toAddSupply)
        }
    }

    private fun onSupppliesList(supplies: List<FeedSupply>) {
        supplyAdapter.items = supplies
    }
}