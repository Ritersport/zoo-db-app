package ru.nsu.databases.ui.animals.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentAnimalDetailsBinding
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.employees.details.EmployeeDetailsFragment
import ru.nsu.databases.ui.utils.toUiDateSting

@AndroidEntryPoint
class AnimalDetailsFragment : BaseFragment() {

    override val viewModel: AnimalDetailsViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentAnimalDetailsBinding.inflate(inflater, container, false)
    }

    private val initialAnimal: Animal by lazy {
        requireNotNull(requireArguments().getParcelable(EmployeeDetailsFragment.ARGS_KEY)) {
            "Employee must be passed to employee details fragment"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAnimal(initialAnimal)
        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        editButton.setOnClickListener { viewModel.onEdit() }
        fireButton.setOnClickListener { viewModel.onExchangeAnimal(initialAnimal) }
        saveButton.setOnClickListener { viewModel.onSaveChanges(initialAnimal) }
        motherLayout.setOnClickListener { viewModel.onGoToParent(initialAnimal.mother!!) }
        fatherLayout.setOnClickListener { viewModel.onGoToParent(initialAnimal.father!!) }
    }


    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(direction: AnimalDetailsFragmentDirections) = when (direction) {
        is AnimalDetailsFragmentDirections.ToParent -> findNavController().navigate(
            R.id.toParent,
            Bundle().apply {
                putParcelable(EmployeeDetailsFragment.ARGS_KEY, direction.parent)
            })
    }

    private fun setAnimal(animal: Animal) = binding.run {
        name.setText(animal.name)
        gender.setText(animal.gender.name)
        birthDate.setText(animal.birthDate?.toUiDateSting())
        nutritionType.setText(animal.kind.nutritionType.name)
        kind.setText(animal.kind.name)
        setMotherInfo(animal.mother)
        setFatherInfo(animal.father)
    }

    private fun FragmentAnimalDetailsBinding.setMotherInfo(mother: AnimalParent?) =
        if (mother != null) {
            motherInfo.text = "Mother: ${mother.name}"
            motherLayout.visibility = View.VISIBLE
        } else {
            motherLayout.visibility = View.GONE
        }

    private fun FragmentAnimalDetailsBinding.setFatherInfo(father: AnimalParent?) =
        if (father != null) {
            fatherInfo.text = "Father: ${father.name}"
            fatherLayout.visibility = View.VISIBLE
        } else {
            fatherLayout.visibility = View.GONE
        }

    companion object {

        const val ARGS_KEY = "AnimalDetailsFragment"
    }
}