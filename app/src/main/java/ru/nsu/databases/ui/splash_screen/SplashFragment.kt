package ru.nsu.databases.ui.splash_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentSplashBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentSplashBinding.inflate(inflater, container, false)
    }

    private val viewModel: SplashFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
    }

    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(event: SplashRoutes) = when (event) {
        SplashRoutes.ToLogin -> findNavController().navigate(R.id.toLogin)
        SplashRoutes.ToMainScreen -> findNavController().navigate(R.id.toMainScreen)
    }
}