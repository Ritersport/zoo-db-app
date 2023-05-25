package ru.nsu.databases.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment : Fragment(), ViewBindingHolder {
    override var _viewBindingDelegate: ViewBindingDelegate<*>? = null
    protected open val binding: ViewBinding? = null

    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _viewBindingDelegate?.create(inflater, container)
            ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showErrorDialog.observe(viewLifecycleOwner, ::onErrorOccurred)

    }

    private fun onErrorOccurred(throwable: Throwable) {

    }
}