@file:JvmName(TAG)

package moe.feng.common.arch.list

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log

private const val TAG = "DataBindingUtils"

@BindingAdapter("bind:items")
fun setItemsWithBindingRecyclerViewAdapter(view: RecyclerView, array: Array<*>?) {
    setItemsWithBindingRecyclerViewAdapter(view, array?.toMutableList())
}

@BindingAdapter("bind:items")
fun setItemsWithBindingRecyclerViewAdapter(view: RecyclerView, list: List<*>?) {
    if (view.adapter !is BindingRecyclerViewAdapter) {
        Log.d(TAG, "Please set BindingRecyclerViewAdapter as adapter of the list")
    } else {
        (view.adapter as? BindingRecyclerViewAdapter)?.data =
                list?.filterNotNull()?.toMutableList() ?: mutableListOf()
        view.adapter?.notifyDataSetChanged()
    }
}