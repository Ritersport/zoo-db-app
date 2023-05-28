package ru.nsu.databases.ui.animals.filter

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.databinding.FragmentAnimalDetailsBinding
import ru.nsu.databases.ui.base.view.BaseFragment
import ru.nsu.databases.ui.base.view.viewBinding

@AndroidEntryPoint
class AnimalFilterFragment : BaseFragment() {

    override val viewModel: AnimalFilterViewModel by viewModels()

    override val binding by viewBinding { inflater, container ->
        FragmentAnimalDetailsBinding.inflate(inflater, container, false)
    }
}