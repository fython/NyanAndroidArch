@file:JvmName("RecyclerViewUtils")
package moe.feng.common.arch.list

import android.support.v7.widget.RecyclerView

/**
 * When RecyclerView should load more contents, action will be done.
 *
 * @param action What should be done when it need to load more
 */
@JvmName("setOnLoadMoreListener")
fun RecyclerView.onLoadMore(action: () -> Unit) {
    this.addOnScrollListener(OnLoadMoreListener(object: OnLoadMoreListener.Callback {
        override fun onLoadMore() {
            action.invoke()
        }
    }))
}