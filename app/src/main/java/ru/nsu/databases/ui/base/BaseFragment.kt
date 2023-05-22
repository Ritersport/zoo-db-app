package ru.nsu.databases.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment : Fragment(), ViewBindingHolder {
    override var _viewBindingDelegate: ViewBindingDelegate<*>? = null
    protected open val binding: ViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _viewBindingDelegate?.create(inflater, container)
            ?: super.onCreateView(inflater, container, savedInstanceState)
    }
}