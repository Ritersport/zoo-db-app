package ru.nsu.databases.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.nsu.databases.ui.base.progress_dialog.ZooProgressDialog


abstract class BaseFragment : Fragment(), ViewBindingHolder {
    override var _viewBindingDelegate: ViewBindingDelegate<*>? = null
    protected open val binding: ViewBinding? = null

    protected abstract val viewModel: BaseViewModel

    private val progressDialog by lazy {
        ZooProgressDialog()
    }

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
        viewModel.isLoading.observe(viewLifecycleOwner, ::setIsLoading)
    }

    private fun setIsLoading(isLoading: Boolean) {
        if (isLoading) {
            progressDialog.show(childFragmentManager, "Loading dialog")
            progressDialog.dialog?.setOnCancelListener { viewModel.onDismiss() }
            progressDialog.dialog?.setOnDismissListener { viewModel.onDismiss() }
        } else {
            progressDialog.dismiss()
        }
    }

    private fun onErrorOccurred(throwable: Throwable) {
        Log.e("Error", throwable.stackTraceToString())
    }
}