package ru.nsu.databases.ui.animals.details

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentAnimalDetailsBinding
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.utils.clearError
import ru.nsu.databases.ui.utils.parseDate
import ru.nsu.databases.ui.utils.setErrorEnable
import ru.nsu.databases.ui.utils.setItems
import ru.nsu.databases.ui.utils.textOrEmpty
import ru.nsu.databases.ui.utils.toUiDateSting

@AndroidEntryPoint
class AnimalDetailsFragment : BaseFragment() {

    override val viewModel: AnimalDetailsViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentAnimalDetailsBinding.inflate(inflater, container, false)
    }

    private val initialAnimal: Animal by lazy {
        requireNotNull(requireArguments().getParcelable(ARGS_KEY)) {
            "Animal must be passed to animal details fragment"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        setupViewListeners()
        viewModel.setAnimal(initialAnimal)
    }

    private fun setupViewListeners() = binding.run {
        editButton.setOnClickListener { viewModel.onEdit() }
        exchangeButton.setOnClickListener { viewModel.onExchangeAnimal(initialAnimal) }
        saveButton.setOnClickListener {
            runCatching {
                clearErrors()
                collectAnimal()?.let { viewModel.onSaveChanges(it) }
            }.onFailure(viewModel::onError)

        }
        motherLayout.setOnClickListener { viewModel.onGoToParent(initialAnimal.mother!!) }
        fatherLayout.setOnClickListener { viewModel.onGoToParent(initialAnimal.father!!) }
        gender.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onGenderSelected(adapter.getItemAtPosition(position) as? Gender)
        }
        kind.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onKindSelected(adapter.getItemAtPosition(position) as? Specie)
        }
    }


    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
        screenState.observe(viewLifecycleOwner, ::onSetScreenState)
        animal.observe(viewLifecycleOwner, this@AnimalDetailsFragment::setAnimal)
        kinds.observe(viewLifecycleOwner, ::onKinds)
        genders.observe(viewLifecycleOwner, ::onGenders)
    }

    private fun obtainNavEvent(direction: AnimalDetailsFragmentDirections) = when (direction) {
        is AnimalDetailsFragmentDirections.ToParent -> findNavController().navigate(
            R.id.toParent,
            Bundle().apply {
                putParcelable(ARGS_KEY, direction.parent)
            })
    }

    private fun setAnimal(animal: Animal) = binding.run {
        name.setText(animal.name)
        gender.setGender(animal.gender)
        birthDate.setText(animal.birthDate?.toUiDateSting())
        nutritionType.setText(animal.kind.nutritionType.name)
        kind.setKind(animal.kind)
        setMotherInfo(animal.mother)
        setFatherInfo(animal.father)
    }

    private fun onSetScreenState(detailsState: DetailsState) = when (detailsState) {
        DetailsState.Add -> binding.setAddState()
        DetailsState.Edit -> binding.setEditState()
        DetailsState.View -> binding.setViewState()
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

    private fun clearErrors() = binding.run {
        genderLayout.clearError()
        nameLayout.clearError()
        kindLayout.clearError()
        birthDateLayout.clearError()
        nutritionTypeLayout.clearError()
    }

    private fun onKinds(kinds: List<Specie>) {
        if (viewModel.selectedKind != null) {
            binding.kind.setItems(this, kinds)
            binding.kind.setText(viewModel.selectedKind?.name, false)

        } else {
            binding.kind.setItems(this, kinds)
        }
    }

    private fun onGenders(genders: List<Gender>) {
        if (viewModel.selectedKind != null) {
            binding.gender.setItems(this, genders)
            binding.gender.setText(viewModel.selectedGender?.name, false)

        } else {
            binding.gender.setItems(this, genders)
        }
    }

    private fun collectAnimal(): Animal? = binding.run {
        val gender = viewModel.selectedGender
        val name = name.textOrEmpty()
        val kind = viewModel.selectedKind

        //validation
        var valid = true
        if (name.isBlank()) {
            valid = false
            nameLayout.setErrorEnable("Fill name")
        }

        if (kind == null) {
            valid = false
            kindLayout.setErrorEnable("Select kind")
        }
        if (gender == null) {
            valid = false
            genderLayout.setErrorEnable("Select gender")
        }
        val birthDate = runCatching { birthDate.parseDate() }.onFailure {
            valid = false
            birthDateLayout.setErrorEnable("Fill birth date try dd.mm.yyyy format")
        }.getOrNull()

        if (valid) {
            Animal(
                id = viewModel.animal.value?.id ?: -1,
                gender = gender!!,
                name = name,
                kind = kind!!,
                birthDate = birthDate!!,
                mother = viewModel.animal.value?.mother,
                father = viewModel.animal.value?.father,
            )
        } else {
            null
        }

    }

    private fun AutoCompleteTextView.setGender(gender: Gender) {
        if (adapter == null) {
            setItems(this@AnimalDetailsFragment, listOf(gender))
            setSelection(0)
            binding.gender.setText(gender.name, false)
        } else {
            (adapter as? ArrayAdapter<Gender>)?.apply {
                val position = getPosition(gender)
                setSelection(position)
                binding.gender.setText(gender.name, false)
            }
        }
        viewModel.onGenderSelected(gender)
    }

    private fun AutoCompleteTextView.setKind(kind: Specie) {
        if (adapter == null) {
            setItems(this@AnimalDetailsFragment, listOf(kind))
            setSelection(0)
            binding.kind.setText(kind.name, false)
        } else {
            (adapter as? ArrayAdapter<Specie>)?.apply {
                val position = getPosition(kind)
                setSelection(position)
                binding.kind.setText(kind.name, false)
            }
        }
        viewModel.onKindSelected(kind)
    }

    private fun FragmentAnimalDetailsBinding.setEditState() = this.run {
        title.text = "Edit"
        nameLayout.isEnabled = true
        kindLayout.isEnabled = true
        genderLayout.isEnabled = true
        birthDateLayout.isEnabled = true
        nutritionTypeLayout.isEnabled = true

        motherLayout.visibility = View.GONE
        fatherLayout.visibility = View.GONE
        nutritionTypeLayout.visibility = View.GONE

        exchangeButton.visibility = View.GONE
        saveButton.visibility = View.VISIBLE
        editButton.visibility = View.GONE
    }

    private fun FragmentAnimalDetailsBinding.setViewState() = this.run {
        title.text = "Animal details"
        nameLayout.isEnabled = false
        kindLayout.isEnabled = false
        genderLayout.isEnabled = false
        birthDateLayout.isEnabled = false
        nutritionTypeLayout.isEnabled = false

        motherLayout.visibility = View.VISIBLE
        fatherLayout.visibility = View.VISIBLE
        nutritionTypeLayout.visibility = View.VISIBLE

        exchangeButton.visibility = View.GONE
        saveButton.visibility = View.GONE
        editButton.visibility = View.VISIBLE
    }

    private fun FragmentAnimalDetailsBinding.setAddState() = this.run {
        title.text = "Add animal"
        nameLayout.isEnabled = true
        kindLayout.isEnabled = true
        nutritionTypeLayout.isEnabled = true
        birthDateLayout.isEnabled = true
        genderLayout.isEnabled = true

        motherLayout.visibility = View.VISIBLE
        fatherLayout.visibility = View.VISIBLE
        nutritionTypeLayout.visibility = View.GONE

        exchangeButton.visibility = View.GONE
        saveButton.visibility = View.VISIBLE
        editButton.visibility = View.GONE
    }

    companion object {

        const val ARGS_KEY = "AnimalDetailsFragment"
    }
}