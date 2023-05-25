package ru.nsu.databases.ui.login_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentLoginBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.SingleLiveEvent
import ru.nsu.databases.ui.base.viewBinding
import ru.nsu.databases.ui.utils.stringOrEmpty

@AndroidEntryPoint
class LogInFragment : BaseFragment() {

    override val viewModel: LogInViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()
        setupViewListeners()
    }

    private fun setupViewListeners() = binding.run {
        submit.setOnClickListener {
            viewModel.onSubmit(
                login = login.stringOrEmpty(),
                password = password.stringOrEmpty()
            )
        }
    }

    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(event: LoginFragmentRoutes) = when (event) {
        LoginFragmentRoutes.ToMain -> {
            findNavController().navigate(R.id.toMainScreen)
        }
    }


}