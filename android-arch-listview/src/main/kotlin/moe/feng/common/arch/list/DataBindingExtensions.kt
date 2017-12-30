@file:JvmName(TAG)

package moe.feng.common.arch.list

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ListAdapter
import android.widget.ListView

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

@BindingAdapter("bind:items")
fun setItemsWithBindingListViewAdapter(view: ListView, array: Array<*>?) {
    setItemsWithBindingListViewAdapter(view, array?.toMutableList())
}

@BindingAdapter("bind:items")
fun setItemsWithBindingListViewAdapter(view: ListView, list: List<*>?) {
    if (view.adapter !is BindingListViewAdapter) {
        Log.d(TAG, "Please set BindingListViewAdapter as adapter of the list")
    } else {
        (view.adapter as? BindingListViewAdapter)?.data =
                list?.filterNotNull()?.toMutableList() ?: mutableListOf()
        (view.adapter as? BindingListViewAdapter)?.notifyDataSetChanged()
    }
}

@BindingAdapter("bind:spanCount")
fun setSpanCount(view: RecyclerView, count: Int) {
    val layoutManager = view.layoutManager
    if (layoutManager is GridLayoutManager) {
        layoutManager.spanCount = count
    } else {
        throw IllegalArgumentException("Unsupported layout manager." +
                " Please use GridLayoutManager instead.")
    }
}

@BindingAdapter("bind:itemsSpan")
fun setItemsSpanCalculator(view: RecyclerView, callback: ISpanSizeLookup) {
    val layoutManager = view.layoutManager
    if (layoutManager is GridLayoutManager) {
        layoutManager.setSpanSizeLookup(callback)
    } else {
        throw IllegalArgumentException("Unsupported layout manager." +
                " Please use GridLayoutManager instead.")
    }
}

@BindingAdapter("bind:autoBindItemsSpan")
fun setAutoBindItemsSpan(view: RecyclerView, value: Boolean) {
    val layoutManager = view.layoutManager
    if (layoutManager is GridLayoutManager) {
        if (value) {
            layoutManager.spanSizeLookupBy { position ->
                (view.adapter as? BindingRecyclerViewAdapter)?.getItemSpanSize(position) ?: 1
            }
        } else {
            layoutManager.spanSizeLookup = null
        }
    } else {
        throw IllegalArgumentException("Unsupported layout manager." +
                " Please use GridLayoutManager instead.")
    }
}

@BindingAdapter("bind:listAdapter")
fun setListAdapter(view: ListView, adapter: ListAdapter) {
    view.adapter = adapter
}

@BindingAdapter("bind:listAdapter")
fun setListAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

interface ISpanSizeLookup {

    fun getSpanSize(position: Int): Int

}