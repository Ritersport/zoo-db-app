package ru.nsu.databases.ui.main_screen

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentMainBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentMainBinding.inflate(inflater, container, false)
    }

    private val viewModel: MainScreenViewModel by viewModels()
}