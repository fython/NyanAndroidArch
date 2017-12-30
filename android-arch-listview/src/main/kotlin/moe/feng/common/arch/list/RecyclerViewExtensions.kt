@file:JvmName("RecyclerViewUtils")
package moe.feng.common.arch.list

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import moe.feng.common.arch.list.callback.OnListLoadMoreListener
import moe.feng.common.arch.list.internal.OnRVLoadMoreListener

/**
 * When RecyclerView should load more contents, action will be done.
 *
 * @param action What should be done when it need to load more
 */
@JvmName("setOnLoadMoreListener")
fun RecyclerView.onLoadMore(action: () -> Unit) {
    this.addOnScrollListener(OnRVLoadMoreListener(object : OnListLoadMoreListener {
        override fun onLoadMore() {
            action.invoke()
        }
    }))
}

fun GridLayoutManager.setSpanSizeLookup(spanSizeLookup: ISpanSizeLookup) {
    this.spanSizeLookupBy(spanSizeLookup::getSpanSize)
}

@JvmName("setSpanSizeLookupCalculator")
fun GridLayoutManager.spanSizeLookupBy(calculator: (position: Int) -> Int) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int = calculator(position)
    }
}