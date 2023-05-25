package ru.nsu.databases.ui.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentMainBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentMainBinding.inflate(inflater, container, false)
    }

    override val viewModel: MainScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        toEmployees.setOnClickListener {
            findNavController().navigate(R.id.toEmployees)
        }
        toAnimals.setOnClickListener {
            findNavController().navigate(R.id.toAnimals)
        }
        toFoods.setOnClickListener {
            findNavController().navigate(R.id.toAnimals)
        }
    }

    private fun setupVmObservers() = viewModel.run {

    }
}