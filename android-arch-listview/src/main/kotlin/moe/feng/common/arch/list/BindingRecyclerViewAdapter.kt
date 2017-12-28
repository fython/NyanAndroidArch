package moe.feng.common.arch.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

open class BindingRecyclerViewAdapter(private val variableId: Int)
    : RecyclerView.Adapter<BindingRecyclerViewAdapter.BindingHolder<Any, ViewDataBinding>>() {

    var data: MutableList<Any> = mutableListOf()

    private val binders: MutableList<Pair<Class<*>, ItemBinder<*, *>>> = mutableListOf()

    fun <T, DB: ViewDataBinding> bind(clazz: Class<T>, binder: ItemBinder<T, DB>)
            : BindingRecyclerViewAdapter {
        binders.add(clazz to binder)
        return this
    }

    inline fun <reified T, DB: ViewDataBinding> bind(binder: ItemBinder<T, DB>)
            : BindingRecyclerViewAdapter {
        return bind(T::class.java, binder)
    }

    fun <T, DB: ViewDataBinding> bind(clazz: Class<T>, @LayoutRes layoutId: Int)
            : BindingRecyclerViewAdapter {
        binders.add(clazz to ItemBinder<T, DB>(layoutId))
        return this
    }

    inline fun <reified T, DB: ViewDataBinding> bind(@LayoutRes layoutId: Int)
            : BindingRecyclerViewAdapter {
        return bind<T, DB>(T::class.java, layoutId)
    }

    fun bindSelf(binder: ItemBinder<*, *>): BindingRecyclerViewAdapter {
        binders.add(binder.javaClass to binder)
        return this
    }

    fun addBinderAsData(binder: ItemBinder<*, *>) {
        if (binders.find { (clazz, _) -> clazz == binder.javaClass } == null) {
            bindSelf(binder)
        }
        data.add(binder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, binderIndex: Int):
            BindingHolder<Any, ViewDataBinding> {
        val binder = getBinder<Any>(binderIndex)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                parent.layoutInflater, binder.layoutId, parent, false)
        return BindingHolder(binding, binder::onViewHolderCreated)
    }

    override fun onBindViewHolder(holder: BindingHolder<Any, ViewDataBinding>, position: Int) {
        holder.currentItem = data[position]
        holder.binding.setVariable(variableId, data[position])
        holder.binding.executePendingBindings()
        getBinderByData(data[position])?.onBindViewHolder(holder, data[position], position)
    }

    override fun onViewRecycled(holder: BindingHolder<Any, ViewDataBinding>?) {
        super.onViewRecycled(holder)
        if (holder != null) {
            getBinderByData(holder.currentItem)?.onViewRecycled(holder)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int =
            binders.indexOfFirst { (clazz, _) -> clazz == data[position].javaClass }

    private fun <T: Any> getBinder(binderIndex: Int): ItemBinder<T, ViewDataBinding> =
            binders[binderIndex].second as ItemBinder<T, ViewDataBinding>

    private fun <T: Any> getBinderByData(data: T?): ItemBinder<T, ViewDataBinding>? =
            binders.find {
                (clazz, _) -> clazz == data?.javaClass
            }?.second as? ItemBinder<T, ViewDataBinding>

    private val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    class BindingHolder<M, out T: ViewDataBinding>(
            val binding: T,
            initBlock: BindingHolder<M, T>.() -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init { initBlock() }

        var currentItem: M? = null

        val context: Context @get:JvmName("getContext") get() = itemView.context

    }

}