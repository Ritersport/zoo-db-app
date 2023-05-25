package ru.nsu.databases.ui.animals.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentAnimalsListBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding

@AndroidEntryPoint
class AnimalsListFragment : BaseFragment() {

    override val binding: FragmentAnimalsListBinding by viewBinding { inflater, container ->
        FragmentAnimalsListBinding.inflate(inflater, container, false)
    }

    override val viewModel: AnimalsListViewModel by viewModels()

    private val animalsAdapter by lazy {
        AnimalsAdapter(
            viewModel::onAnimalClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animalsList.adapter = animalsAdapter
        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {

    }

    private fun setupVmObservers() = viewModel.run {
        animals.observe(viewLifecycleOwner, ::onAnimalsList)
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(direction: AnimalsListDirections) = when (direction) {
        AnimalsListDirections.ToAddEmployee -> {

        }

        is AnimalsListDirections.ToEmployeeDetails -> {

        }
    }

    private fun onAnimalsList(animals: List<AnimalMigrationWrapper>) {
        animalsAdapter.items = animals
    }
}