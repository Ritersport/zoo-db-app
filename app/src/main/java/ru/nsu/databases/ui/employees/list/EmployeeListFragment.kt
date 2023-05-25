package ru.nsu.databases.ui.employees.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentEmployeesListBinding
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding
import ru.nsu.databases.ui.employees.details.EmployeeDetailsFragment

@AndroidEntryPoint
class EmployeeListFragment : BaseFragment() {
    override val viewModel: EmployeeListViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentEmployeesListBinding.inflate(inflater, container, false)
    }

    private val employeeAdapter by lazy {
        EmployeeAdapter(
            viewModel::onEmployeeClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.employeeList.adapter = employeeAdapter
        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        addEmployee.setOnClickListener { viewModel.onAddEmployee() }
    }

    private fun setupVmObservers() = viewModel.run {
        employees.observe(viewLifecycleOwner, ::onEmployeeList)
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(direction: EmployeesListDirections) = when (direction) {
        EmployeesListDirections.ToAddEmployee -> {
            findNavController().navigate(R.id.toAddEmployee)
        }

        is EmployeesListDirections.ToEmployeeDetails -> {
            findNavController().navigate(R.id.toEmployeeDetails, Bundle().apply {
                putParcelable(EmployeeDetailsFragment.ARGS_KEY, direction.employee)
            })
        }
    }

    private fun onEmployeeList(employees: List<Employee>) {
        employeeAdapter.items = employees
    }
}