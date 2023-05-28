package ru.nsu.databases.ui.supplies.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentAddSupplyBinding
import ru.nsu.databases.domain.model.zoo.FeedSupply
import ru.nsu.databases.domain.model.zoo.Vendor
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.utils.parseDate
import ru.nsu.databases.ui.utils.setItems

@AndroidEntryPoint
class AddSupplyFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentAddSupplyBinding.inflate(inflater, container, false)
    }

    override val viewModel: AddSupplyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        add.setOnClickListener {
            if (checkFields()) {
                runCatching {
                    viewModel.onSubmit(collectSupply())
                }.exceptionOrNull()?.let {
                    viewModel.onError(it)
                }
            }
        }
        vendors.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onVendorSelected(adapter.getItemAtPosition(position) as? Vendor)
            binding.add.isEnabled = true
        }
    }

    private fun checkFields(): Boolean = binding.run {
        !amount.text.isNullOrBlank() &&
                !date.text.isNullOrBlank() &&
                !vendors.text.isNullOrBlank() &&
                !price.text.isNullOrBlank()

    }


    private fun collectSupply(): FeedSupply {

        val supplyDate = binding.date.parseDate()
            ?: throw IllegalStateException("Failed to parse date, try dd.mm.yyyy format")

        return FeedSupply(
            vendor = viewModel.vendor ?: throw IllegalStateException("Select vendor"),
            supplyDate = supplyDate,
            amount = binding.amount.text?.toString()?.toInt()
                ?: throw IllegalStateException("Illegal amount"),
            price = binding.price.text?.toString()?.toInt()
                ?: throw IllegalStateException("Illegal price"),
        )
    }

    private fun setupVmObservers() = viewModel.run {
        vendors.observe(viewLifecycleOwner, ::onVendors)
        goBack.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun onVendors(vendor: List<Vendor>) = binding.run {
        vendors.setItems(this@AddSupplyFragment, vendor)
    }
}