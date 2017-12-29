package moe.feng.common.arch.list

import android.content.Context
import android.databinding.ViewDataBinding

interface IBindingViewHolder<M, out T: ViewDataBinding> {

    fun getAdapterPosition(): Int

    var currentItem: M?

    val context: Context

    val binding: T

}