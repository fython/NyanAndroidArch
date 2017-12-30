package moe.feng.common.arch.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

open class BindingListViewAdapter(private val variableId: Int)
    : BaseAdapter(), IBindingAdapter {

    private var _data: MutableList<Any> = mutableListOf()
    final override var data: MutableList<Any>
        get() = _data
        set(value) { _data = value }
    private val _binders: MutableList<Pair<Class<*>, BindingItemBinder<*, *>>> = mutableListOf()
    final override val binders: MutableList<Pair<Class<*>, BindingItemBinder<*, *>>>
        get() = _binders

    private fun onCreateView(parent: ViewGroup, position: Int): View {
        val binder = getBinderByDataPosition<Any>(position)
                ?: throw IllegalArgumentException("No binder for position=$position")
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                parent.layoutInflater, binder.layoutId, parent, false
        ).apply { root.tag = BindingHolder(this, binder::onViewHolderCreated) }
        return binding.root
    }

    private fun onBindView(view: View, position: Int) {
        val holder = view.tag as BindingHolder<Any, ViewDataBinding>
        holder.setAdapterPosition(position)
        holder.currentItem = data[position]
        holder.binding.setVariable(variableId, data[position])
        holder.binding.executePendingBindings()
        getBinderByDataPosition<Any>(position)?.onBindViewHolder(holder, data[position], position)
    }

    final override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: onCreateView(parent, position)
        this.onBindView(view, position)
        return view
    }

    final override fun getItem(position: Int): Any = data[position]

    final override fun getItemId(position: Int): Long = position.toLong()

    final override fun getCount(): Int = data.size

    final override fun getViewTypeCount(): Int = binders.size

    final override fun getItemViewType(position: Int): Int = getBinderIndexByDataPosition(position)

    class BindingHolder<M, out T: ViewDataBinding>(
            override val binding: T,
            initBlock: BindingListViewAdapter.BindingHolder<M, T>.() -> Unit
    ): IBindingViewHolder<M, T> {

        init { initBlock() }

        private var _adapterPosition = -1
        override fun getAdapterPosition(): Int = _adapterPosition
        internal fun setAdapterPosition(adapterPosition: Int) {
            _adapterPosition = adapterPosition
        }

        private var _currentItem: M? = null
        override var currentItem: M?
            get() = _currentItem
            set(value) { _currentItem = value }
        override val context: Context
            get() = binding.root.context

    }

}