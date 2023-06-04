package ru.nsu.databases.ui.help.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentHelpMainBinding
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.BaseViewModel
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class HelpMainFragment : BaseFragment() {

    override val viewModel: BaseViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentHelpMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        toAnimalCompatibilityCard.setOnClickListener {
            findNavController().navigate(R.id.toAnimalCompatibility)
        }
    }
}