package moe.feng.common.arch.list

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

interface IBindingAdapter {

    var data: MutableList<Any>

    val binders: MutableList<Pair<Class<*>, BindingItemBinder<*, *>>>

    fun <T, DB: ViewDataBinding> bind(clazz: Class<T>, binder: BindingItemBinder<T, DB>)
            : IBindingAdapter {
        binders.add(clazz to binder)
        return this
    }

    fun <T, DB: ViewDataBinding> bind(clazz: Class<T>, @LayoutRes layoutId: Int): IBindingAdapter {
        binders.add(clazz to BindingItemBinder<T, DB>(layoutId))
        return this
    }

    fun bindSelf(binder: BindingItemBinder<*, *>): IBindingAdapter {
        binders.add(binder.javaClass to binder)
        return this
    }

    fun addBinderAsData(binder: BindingItemBinder<*, *>) {
        if (binders.find { (clazz, _) -> clazz.isInstance(binder) } == null) {
            bindSelf(binder)
        }
        data.add(binder)
    }

    fun <T: Any> getBinder(binderIndex: Int): BindingItemBinder<T, ViewDataBinding> =
            binders[binderIndex].second as BindingItemBinder<T, ViewDataBinding>

    fun getBinderIndexByDataPosition(position: Int): Int =
            binders.indexOfFirst { (clazz, _) -> clazz.isInstance(data[position]) }

    fun <T: Any> getBinderByDataPosition(position: Int): BindingItemBinder<T, ViewDataBinding>? =
            binders.find {
                (clazz, _) -> clazz.isInstance(data[position])
            }?.second as? BindingItemBinder<T, ViewDataBinding>

    fun <T: Any> getBinderByData(data: T?): BindingItemBinder<T, ViewDataBinding>? =
            binders.find {
                (clazz, _) -> clazz.isInstance(data)
            }?.second as? BindingItemBinder<T, ViewDataBinding>

}

inline fun <reified T, DB: ViewDataBinding>
        IBindingAdapter.bind(binder: BindingItemBinder<T, DB>): IBindingAdapter {
    return bind(T::class.java, binder)
}

inline fun <reified T, DB: ViewDataBinding>
        IBindingAdapter.bind(@LayoutRes layoutId: Int): IBindingAdapter {
    return bind<T, DB>(T::class.java, layoutId)
}

internal val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)
