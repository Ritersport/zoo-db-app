package ru.nsu.databases.ui.base.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingDelegate<T : ViewBinding>(
    private val onViewBindingCreate: (inflater: LayoutInflater, container: ViewGroup?) -> T
) : PropertyDelegateProvider<ViewBindingHolder, ViewBindingDelegate<T>>,
    ReadOnlyProperty<ViewBindingHolder, T> {
    private var binding: T? = null
    fun create(inflater: LayoutInflater, container: ViewGroup? = null): View {
        val binding = onViewBindingCreate(inflater, container)
        this.binding = binding
        return binding.root
    }

    fun clear() {
        binding = null
    }

    override fun provideDelegate(
        thisRef: ViewBindingHolder,
        property: KProperty<*>
    ): ViewBindingDelegate<T> {
        thisRef._viewBindingDelegate = this
        return this
    }

    override fun getValue(thisRef: ViewBindingHolder, property: KProperty<*>): T {
        return binding!!
    }
}

fun <T : ViewBinding> viewBinding(
    onViewBindingCreate: (inflater: LayoutInflater, container: ViewGroup?) -> T
): ViewBindingDelegate<T> {
    return ViewBindingDelegate(onViewBindingCreate)
}

interface ViewBindingHolder {
    @Suppress("PropertyName")
    var _viewBindingDelegate: ViewBindingDelegate<*>?
}