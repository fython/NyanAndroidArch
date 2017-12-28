package moe.feng.common.arch

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BindingFragment<out T: ViewDataBinding> : BaseFragment() {

    private var _binding: T? = null
    internal val binding: T? @get:JvmName("getBinding") get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = DataBindingUtil
            .inflate<T>(inflater, getDefaultLayoutId(), container, false)
            .apply { _binding = this }
            .root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}