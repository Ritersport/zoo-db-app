package ru.nsu.databases.ui.employees.details

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentEmployeeDetailsBinding
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.Profession
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding
import ru.nsu.databases.ui.utils.clearError
import ru.nsu.databases.ui.utils.parseDate
import ru.nsu.databases.ui.utils.setErrorEnable
import ru.nsu.databases.ui.utils.setItems
import ru.nsu.databases.ui.utils.textOrEmpty
import ru.nsu.databases.ui.utils.toUiDateSting

@AndroidEntryPoint
class EmployeeDetailsFragment : BaseFragment() {

    override val viewModel: EmployeeDetailsViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentEmployeeDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        setupViewListeners()

        val employee: Employee? = arguments?.getParcelable(ARGS_KEY)
        viewModel.setEmployee(employee)
    }

    private fun setEmployee(employee: Employee) = binding.run {
        name.setText(employee.name)
        surname.setText(employee.surname)
        patronymic.setText(employee.patronymic)
        birthDate.setText(employee.birthDate.toUiDateSting())
        profession.setProfession(employee.profession)
        gender.setGender(employee.gender)
        salary.setText(employee.salary.toString())
        employmentDate.setText(employee.employmentDate.toUiDateSting())
        dismissalDate.setText(employee.dismissalDate.toUiDateSting())
    }

    private fun setupViewListeners() = binding.run {
        editButton.setOnClickListener { viewModel.setEditState() }
        fireButton.setOnClickListener { viewModel.onFireEmployee() }
        saveButton.setOnClickListener {
            runCatching {
                clearErrors()
                collectEmployee()?.let { viewModel.onSaveChanges(it) }
            }.onFailure(viewModel::onError)

        }
        gender.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onGenderSelected(adapter.getItemAtPosition(position) as? Gender)
        }
        profession.setOnItemClickListener { adapter, _, position, _ ->
            viewModel.onProfessionSelected(adapter.getItemAtPosition(position) as? Profession)
        }
    }

    private fun clearErrors() = binding.run {
        genderLayout.clearError()
        nameLayout.clearError()
        surnameLayout.clearError()
        birthDateLayout.clearError()
        professionLayout.clearError()
        genderLayout.clearError()
        salaryLayout.clearError()
        employmentDateLayout.clearError()
        professionLayout.clearError()
    }

    private fun collectEmployee(): Employee? = binding.run {
        val gender = viewModel.selectedGender
        val name = name.textOrEmpty()
        val surname = surname.textOrEmpty()
        val patronymic = patronymic.textOrEmpty()
        val profession = viewModel.selectedProfession
        val salary = salary.textOrEmpty().toIntOrNull()

        //validation
        var valid = true
        if (name.isBlank()) {
            valid = false
            nameLayout.setErrorEnable("Fill name")
        }
        if (surname.isBlank()) {
            valid = false
            surnameLayout.setErrorEnable("Fill surname")
        }
        if (profession == null) {
            valid = false
            professionLayout.setErrorEnable("Select profession")
        }
        if (gender == null) {
            valid = false
            genderLayout.setErrorEnable("Select gender")
        }
        if (salary == null) {
            valid = false
            salaryLayout.setErrorEnable("Fill salary")
        }
        val birthDate = runCatching { birthDate.parseDate() }.onFailure {
            valid = false
            birthDateLayout.setErrorEnable("Fill birth date try dd.mm.yyyy format")
        }.getOrNull()
        val employmentDate = runCatching { employmentDate.parseDate() }.onFailure {
            valid = false
            employmentDateLayout.setErrorEnable("Fill employment date try dd.mm.yyyy format")
        }.getOrNull()

        if (valid) {
            Employee(
                id = viewModel.employee.value?.id ?: -1,
                gender = gender!!,
                name = name,
                surname = surname,
                patronymic = patronymic,
                profession = profession!!,
                birthDate = birthDate!!,
                employmentDate = employmentDate!!,
                salary = salary!!,
                dismissalDate = null,
            )
        } else {
            null
        }

    }

    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::navigateBack)
        screenState.observe(viewLifecycleOwner, ::onSetScreenState)
        employee.observe(viewLifecycleOwner, this@EmployeeDetailsFragment::setEmployee)
        professions.observe(viewLifecycleOwner, ::onProfessions)
        genders.observe(viewLifecycleOwner, ::onGenders)
    }

    private fun AutoCompleteTextView.setProfession(profession: Profession) {
        if (adapter == null) {
            setItems(this@EmployeeDetailsFragment, listOf(profession))
            setSelection(0)
            binding.profession.setText(profession.name, false)
        } else {
            (adapter as? ArrayAdapter<Profession>)?.apply {
                val position = getPosition(profession)
                setSelection(position)
                binding.profession.setText(profession.name, false)
            }
        }
        viewModel.onProfessionSelected(profession)
    }

    private fun AutoCompleteTextView.setGender(gender: Gender) {
        if (adapter == null) {
            setItems(this@EmployeeDetailsFragment, listOf(gender))
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

    private fun onProfessions(professions: List<Profession>) {
        if (viewModel.selectedProfession != null) {
            binding.profession.setItems(this, professions)
            binding.profession.setText(viewModel.selectedProfession?.name, false)

        } else {
            binding.profession.setItems(this, professions)
        }
    }

    private fun onGenders(genders: List<Gender>) {
        if (viewModel.selectedProfession != null) {
            binding.gender.setItems(this, genders)
            binding.gender.setText(viewModel.selectedGender?.name, false)

        } else {
            binding.gender.setItems(this, genders)
        }
    }

    private fun onSetScreenState(detailsState: DetailsState) = when (detailsState) {
        DetailsState.Add -> binding.setAddState()
        DetailsState.Edit -> binding.setEditState()
        DetailsState.View -> binding.setViewState()
    }

    private fun navigateBack(direction: Unit) {
        findNavController().popBackStack()
    }

    private fun FragmentEmployeeDetailsBinding.setAddState() = this.run {
        title.text = "Add employee"
        nameLayout.isEnabled = true
        surnameLayout.isEnabled = true
        patronymicLayout.isEnabled = true
        birthDateLayout.isEnabled = true
        professionLayout.isEnabled = true
        genderLayout.isEnabled = true
        salaryLayout.isEnabled = true
        employmentDateLayout.isEnabled = true
        dismissalDateLayout.isEnabled = true

        patronymicLayout.visibility = View.VISIBLE
        dismissalDateLayout.visibility = View.GONE

        fireButton.visibility = View.GONE
        saveButton.visibility = View.VISIBLE
        editButton.visibility = View.GONE
    }

    private fun FragmentEmployeeDetailsBinding.setEditState() = this.run {
        title.text = "Employee details"
        nameLayout.isEnabled = true
        surnameLayout.isEnabled = true
        patronymicLayout.isEnabled = true
        birthDateLayout.isEnabled = true
        professionLayout.isEnabled = true
        genderLayout.isEnabled = true
        salaryLayout.isEnabled = true
        employmentDateLayout.isEnabled = true

        patronymicLayout.visibility = View.VISIBLE
        dismissalDateLayout.isVisible = (viewModel.employee.value?.isDismissed == true)
        patronymicLayout.isVisible = (viewModel.employee.value?.patronymic != null)
        fireButton.visibility = View.GONE
        saveButton.visibility = View.VISIBLE
        editButton.visibility = View.GONE
    }

    private fun FragmentEmployeeDetailsBinding.setViewState() = this.run {
        title.text = "Employee details"
        nameLayout.isEnabled = false
        surnameLayout.isEnabled = false
        patronymicLayout.isEnabled = false
        birthDateLayout.isEnabled = false
        professionLayout.isEnabled = false
        genderLayout.isEnabled = false
        salaryLayout.isEnabled = false
        employmentDateLayout.isEnabled = false
        dismissalDateLayout.isEnabled = false

        patronymicLayout.isVisible = (viewModel.employee.value?.patronymic != null)
        dismissalDateLayout.isVisible = (viewModel.employee.value?.isDismissed == true)

        if (viewModel.employee.value?.isDismissed == true) {
            fireButton.visibility = View.GONE
            saveButton.visibility = View.GONE
            editButton.visibility = View.GONE
        } else {
            fireButton.visibility = View.VISIBLE
            saveButton.visibility = View.GONE
            editButton.visibility = View.VISIBLE
        }
    }

    companion object {

        const val ARGS_KEY = "EmployeeDetailsFragmentArgs"
    }
}