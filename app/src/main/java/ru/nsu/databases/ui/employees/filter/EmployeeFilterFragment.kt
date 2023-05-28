package ru.nsu.databases.ui.employees.filter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentEmployeeFilterBinding
import ru.nsu.databases.domain.model.zoo.Profession
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.utils.setItems

@AndroidEntryPoint
class EmployeeFilterFragment : BaseFragment() {

    override val viewModel: EmployeeFilterViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentEmployeeFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        clearButton.setOnClickListener {
            minSalary.text = null
            maxSalary.text = null
            showFiredSwitch.isActivated = false
            profession.text = null

            viewModel.onClearFilters()
        }
        applyButton.setOnClickListener {
            val filter = collectEmployeeFilter()
            parentFragmentManager.setFragmentResult(
                FILTER_REQUEST_KEY, bundleOf(
                    FILTER_KEY to filter
                )
            )
            findNavController().popBackStack()
        }
        profession.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onProfessionSelected(adapter.getItemAtPosition(position) as? Profession)
        }
    }

    private fun collectEmployeeFilter(): EmployeeFilter = binding.run {
        val minSalary = runCatching { minSalary.text.toString().toInt() }.getOrNull()
        val maxSalary = runCatching { maxSalary.text.toString().toInt() }.getOrNull()

        EmployeeFilter(
            maxSalary = maxSalary,
            minSalary = minSalary,
            profession = viewModel.profession,
            showFired = showFiredSwitch.isChecked
        )
    }

    private fun setupVmObservers() = viewModel.run {
        professions.observe(viewLifecycleOwner, ::onProfessions)
    }

    private fun onProfessions(value: List<Profession>) = binding.run {
        profession.setItems(this@EmployeeFilterFragment, value)
        applyButton.isEnabled = checkIfApplyAvailable()
    }

    private fun checkIfApplyAvailable(): Boolean = viewModel.professions.isInitialized

    companion object {

        const val FILTER_REQUEST_KEY = "FILTER_REQUEST_KEY"
        const val FILTER_KEY = "FILTER_KEY"
    }

}