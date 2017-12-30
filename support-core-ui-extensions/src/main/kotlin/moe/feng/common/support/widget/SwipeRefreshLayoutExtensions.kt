@file:JvmName("SwipeRefreshLayoutUtils")
package moe.feng.common.support.widget

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout

@BindingAdapter("bind:onRefresh")
fun setOnRefreshListener(
        refreshLayout: SwipeRefreshLayout,
        listener: SwipeRefreshLayout.OnRefreshListener
) {
    refreshLayout.setOnRefreshListener(listener)
}

@BindingAdapter("bind:isRefreshing")
fun setRefreshing(refreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    refreshLayout.isRefreshing = isRefreshing
}