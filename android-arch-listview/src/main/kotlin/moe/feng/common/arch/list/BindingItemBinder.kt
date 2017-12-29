package moe.feng.common.arch.list

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes

open class BindingItemBinder<M, in T: ViewDataBinding>(@LayoutRes val layoutId: Int) {

    open fun onViewHolderCreated(holder: IBindingViewHolder<M, T>) {

    }

    open fun onBindViewHolder(
            holder: IBindingViewHolder<M, T>,
            data: M,
            position: Int
    ) {

    }

    open fun onViewRecycled(holder: IBindingViewHolder<M, T>) {

    }

}