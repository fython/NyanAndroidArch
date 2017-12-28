package moe.feng.common.arch

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle

abstract class BindingActivity<out T: ViewDataBinding>: BaseActivity() {

    private var _binding: T? = null
    protected val binding: T @get:JvmName("getBinding") get() = _binding!!

    override val autoBindDefaultLayout: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (defaultLayoutId > 0) {
            _binding = DataBindingUtil.setContentView(this, defaultLayoutId)
        }
        onViewCreated(savedInstanceState)
    }

    abstract fun onViewCreated(savedInstanceState: Bundle?)

}