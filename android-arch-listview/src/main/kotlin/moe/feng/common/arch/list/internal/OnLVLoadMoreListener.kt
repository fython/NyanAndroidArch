package moe.feng.common.arch.list.internal

import android.widget.AbsListView
import moe.feng.common.arch.list.callback.OnListLoadMoreListener


/**
 * A type of ListView OnScrollListener help you to listen load more event
 */
class OnLVLoadMoreListener(private val callback: OnListLoadMoreListener)
    : MultiOnScrollListener<OnLVLoadMoreListener>() {

    private var currentScrollState: Int = 0

    override fun onScroll(
            view: AbsListView?,
            firstVisibleItem: Int,
            visibleItemCount: Int,
            totalItemCount: Int
    ) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
        if (visibleItemCount == totalItemCount) {
            return
        }

        val loadMore = firstVisibleItem + visibleItemCount >= totalItemCount
        if (loadMore && currentScrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            callback.onLoadMore()
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        super.onScrollStateChanged(view, scrollState)
        currentScrollState = scrollState
    }

}