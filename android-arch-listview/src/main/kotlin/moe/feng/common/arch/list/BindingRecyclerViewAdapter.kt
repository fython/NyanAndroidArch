package moe.feng.common.arch.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class BindingRecyclerViewAdapter(private val variableId: Int)
    : RecyclerView.Adapter<BindingRecyclerViewAdapter.BindingHolder<Any, ViewDataBinding>>(), IBindingAdapter {

    private var _data: MutableList<Any> = mutableListOf()
    final override var data: MutableList<Any>
        get() = _data
        set(value) { _data = value }
    private val _binders: MutableList<Pair<Class<*>, BindingItemBinder<*, *>>> = mutableListOf()
    final override val binders: MutableList<Pair<Class<*>, BindingItemBinder<*, *>>>
        get() = _binders

    final override fun onCreateViewHolder(parent: ViewGroup, binderIndex: Int):
            BindingHolder<Any, ViewDataBinding> {
        val binder = getBinder<Any>(binderIndex)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                parent.layoutInflater, binder.layoutId, parent, false)
        return BindingHolder(binding, binder::onViewHolderCreated)
    }

    final override fun onBindViewHolder(holder: BindingHolder<Any, ViewDataBinding>, position: Int) {
        holder.currentItem = data[position]
        holder.binding.setVariable(variableId, data[position])
        holder.binding.executePendingBindings()
        getBinderByData(data[position])?.onBindViewHolder(holder, data[position], position)
    }

    final override fun onViewRecycled(holder: BindingHolder<Any, ViewDataBinding>?) {
        super.onViewRecycled(holder)
        if (holder != null) {
            getBinderByData(holder.currentItem)?.onViewRecycled(holder)
        }
    }

    final override fun getItemCount(): Int = data.size

    final override fun getItemViewType(position: Int): Int = getBinderIndexByDataPosition(position)

    fun getItemSpanSize(position: Int): Int =
            getBinderByDataPosition<Any>(position)?.getItemSpanSize(data[position], position) ?: 1

    class BindingHolder<M, out T: ViewDataBinding>(
            override val binding: T,
            initBlock: BindingHolder<M, T>.() -> Unit
    ): RecyclerView.ViewHolder(binding.root), IBindingViewHolder<M, T> {

        init { initBlock() }

        private var _currentItem: M? = null
        override var currentItem: M?
            get() = _currentItem
            set(value) { _currentItem = value }

        override val context: Context get() = itemView.context

    }

}