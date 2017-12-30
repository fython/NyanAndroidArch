package moe.feng.common.arch.list

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes

open class BindingItemBinder<M, in T: ViewDataBinding>(@LayoutRes val layoutId: Int) {

    /**
     * Call after view holder created
     *
     * @param holder View holder containing ViewDataBinding
     * @see IBindingViewHolder
     * @see ViewDataBinding
     */
    open fun onViewHolderCreated(holder: IBindingViewHolder<M, T>) {

    }

    /**
     * Call after finishing data binding.
     *
     * @param holder View holder containing ViewDataBinding
     * @param data Data which needs to bind
     * @param position The position of data
     * @see IBindingViewHolder
     * @see ViewDataBinding
     */
    open fun onBindViewHolder(
            holder: IBindingViewHolder<M, T>,
            data: M,
            position: Int
    ) {

    }

    /**
     * Get span size of items. This method is only supported in RecyclerView.
     * <p>
     * RecyclerView should set GridLayoutManager as default layout manager when requiring span set.
     *
     * @param data The data of item
     * @param position The position of item
     * @return Span size of item
     */
    open fun getItemSpanSize(data: M, position: Int) = 1

    /**
     * Call when view is recycled. This method is only supported in RecyclerView.
     *
     * @param holder View holder containing ViewDataBinding
     * @see IBindingAdapter
     */
    open fun onViewRecycled(holder: IBindingViewHolder<M, T>) {

    }

}