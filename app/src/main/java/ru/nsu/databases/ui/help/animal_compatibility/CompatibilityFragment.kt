package ru.nsu.databases.ui.help.animal_compatibility

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentHelpCompatibilityBinding
import ru.nsu.databases.domain.model.zoo.IncompatibleSpecies
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class CompatibilityFragment : BaseFragment() {

    override val viewModel: CompatibilityViewModel by viewModels()

    private val adapter: CompatibilityAdapter by lazy {
        CompatibilityAdapter()
    }

    override val binding by viewBinding { inflater, container ->
        FragmentHelpCompatibilityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        binding.list.adapter = adapter
    }

    private fun setupVmObservers() = viewModel.run {
        species.observe(viewLifecycleOwner, ::setIncompatibleSpecies)
    }

    private fun setIncompatibleSpecies(incompatibleSpecies: List<IncompatibleSpecies>) {
        adapter.items = incompatibleSpecies
    }
}