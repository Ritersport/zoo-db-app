package ru.nsu.databases.ui.employees.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentEmployeeDetailsBinding
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class EmployeeDetailsFragment : BaseFragment() {
    override val viewModel: EmployeeDetailsViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentEmployeeDetailsBinding.inflate(inflater, container, false)
    }

    private val initialEmployee: Employee by lazy {
        requireNotNull(requireArguments().getParcelable(ARGS_KEY)) {
            "Employee must be passed to employee details fragment"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEmployee()
        setupVmObservers()
        setupViewListeners()
    }

    private fun initEmployee() = binding.run {
        name.setText(initialEmployee.name)
        surname.setText(initialEmployee.surname)
        patronymic.setTextOrGone(initialEmployee.patronymic)
        birthDate.setText(initialEmployee.birthDate.toString())
        profession.setText(initialEmployee.profession.name)
        salary.setText(initialEmployee.salary.toString())
        employmentDate.setText(initialEmployee.employmentDate.toString())
        dismissalDate.setTextOrGone(initialEmployee.dismissalDate?.toString())
    }

    private fun setupViewListeners() = binding.run {
        editButton.setOnClickListener { viewModel.onEdit() }
        fireButton.setOnClickListener { viewModel.onFireEmployee(initialEmployee) }
        saveButton.setOnClickListener { viewModel.onSaveChanges(initialEmployee) }
    }

    private fun collectEmployeeInfo(): Employee = initialEmployee

    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::navigateBack)
        isEdit.observe(viewLifecycleOwner, ::onSetEditState)
    }

    private fun onSetEditState(isEdit: Boolean) = binding.run {
        //TODO
    }

    private fun navigateBack(direction: Unit) {
        findNavController().popBackStack()
    }

    private fun TextInputEditText.setTextOrGone(text: String?) {
        if (text == null) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
            this.setText(text)
        }
    }

    companion object {

        const val ARGS_KEY = "EmployeeDetailsFragmentArgs"
    }
}