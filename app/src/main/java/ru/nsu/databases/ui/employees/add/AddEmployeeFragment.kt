package ru.nsu.databases.ui.employees.add

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentAddEmployeeBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding

@AndroidEntryPoint
class AddEmployeeFragment : BaseFragment() {
    private val viewModel: AddEmployeeViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentAddEmployeeBinding.inflate(inflater, container, false)
    }

}