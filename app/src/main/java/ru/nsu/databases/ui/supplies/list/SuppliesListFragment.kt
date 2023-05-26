package ru.nsu.databases.ui.supplies.list

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.ui.base.view.BaseFragment

@AndroidEntryPoint
class SuppliesListFragment : BaseFragment() {

    override val viewModel: SuppliesListViewModel by viewModels()

}