package moe.feng.common.arch.list

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes

open class ItemBinder<M, in T: ViewDataBinding>(@LayoutRes val layoutId: Int) {

    open fun onViewHolderCreated(holder: BindingRecyclerViewAdapter.BindingHolder<M, T>) {

    }

    open fun onBindViewHolder(
            holder: BindingRecyclerViewAdapter.BindingHolder<M, T>,
            data: M,
            position: Int
    ) {

    }

    open fun onViewRecycled(holder: BindingRecyclerViewAdapter.BindingHolder<M, T>) {

    }

}