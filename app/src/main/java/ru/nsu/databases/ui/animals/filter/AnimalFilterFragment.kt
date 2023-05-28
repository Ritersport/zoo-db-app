package ru.nsu.databases.ui.animals.filter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentAnimalFilterBinding
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.utils.setItems

@AndroidEntryPoint
class AnimalFilterFragment : BaseFragment() {

    override val viewModel: AnimalFilterViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentAnimalFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        clearButton.setOnClickListener {
            nutritionType.text = null
            gender.text = null
            kind.text = null
            minAge.text = null
            maxAge.text = null

            viewModel.onClearFilters()
        }
        applyButton.setOnClickListener {
            val filter = collectAnimalFilter()
            parentFragmentManager.setFragmentResult(
                FILTER_REQUEST_KEY, bundleOf(
                    FILTER_KEY to filter
                )
            )
            findNavController().popBackStack()
        }
        kind.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onSpecieSelected(adapter.getItemAtPosition(position) as? Specie)
        }
        gender.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onGenderSelected(adapter.getItemAtPosition(position) as? Gender)
        }
        nutritionType.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onNutritionTypeSelected(adapter.getItemAtPosition(position) as? NutritionType)
        }
    }

    private fun collectAnimalFilter(): AnimalFilter = binding.run {
        val minAge = runCatching { minAge.text.toString().toInt() }.getOrNull()
        val maxAge = runCatching { maxAge.text.toString().toInt() }.getOrNull()

        AnimalFilter(
            kind = viewModel.specie,
            minAgeMonth = minAge,
            maxAgeMonth = maxAge,
            gender = viewModel.gender,
            nutritionType = viewModel.nutritionType,
        )
    }

    private fun setupVmObservers() = viewModel.run {
        nutritionTypes.observe(viewLifecycleOwner, ::onNutritionTypes)
        species.observe(viewLifecycleOwner, ::onSpecies)
        genders.observe(viewLifecycleOwner, ::onGenders)
    }

    private fun checkIfApplyAvailable(): Boolean = viewModel.run {
        genders.isInitialized || species.isInitialized || nutritionTypes.isInitialized
    }

    private fun onNutritionTypes(value: List<NutritionType>) = binding.run {
        nutritionType.setItems(this@AnimalFilterFragment, value)
        applyButton.isEnabled = checkIfApplyAvailable()
    }

    private fun onGenders(value: List<Gender>) = binding.run {
        gender.setItems(this@AnimalFilterFragment, value)
        applyButton.isEnabled = checkIfApplyAvailable()
    }

    private fun onSpecies(value: List<Specie>) = binding.run {
        kind.setItems(this@AnimalFilterFragment, value)
        applyButton.isEnabled = checkIfApplyAvailable()
    }

    companion object {

        const val FILTER_REQUEST_KEY = "FILTER_REQUEST_KEY"
        const val FILTER_KEY = "FILTER_KEY"
    }

}