@file:JvmName("ListViewUtils")
package moe.feng.common.arch.list

import android.widget.ListView
import moe.feng.common.arch.list.callback.OnListLoadMoreListener
import moe.feng.common.arch.list.internal.DefaultMultiOnScrollListener
import moe.feng.common.arch.list.internal.OnLVLoadMoreListener

/**
 * When ListView should load more contents, action will be done.
 *
 * @param action What should be done when it need to load more
 */
@JvmName("setOnLoadMoreListener")
fun ListView.onLoadMore(action: () -> Unit): OnLVLoadMoreListener {
    val listener = OnLVLoadMoreListener(object : OnListLoadMoreListener {
        override fun onLoadMore() {
            action.invoke()
        }
    })
    this.setOnScrollListener(listener)
    return listener.addNextListener(object : DefaultMultiOnScrollListener() {

    })
}