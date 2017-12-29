package moe.feng.common.arch.list.internal

import android.support.annotation.CallSuper
import android.widget.AbsListView

open class DefaultMultiOnScrollListener : MultiOnScrollListener<DefaultMultiOnScrollListener>()

abstract class MultiOnScrollListener<T> : AbsListView.OnScrollListener
        where T : MultiOnScrollListener<T> {

    private var nextListener: MultiOnScrollListener<*>? = null

    @CallSuper
    override fun onScroll(
            view: AbsListView?,
            firstVisibleItem: Int,
            visibleItemCount: Int,
            totalItemCount: Int
    ) {
        nextListener?.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
    }

    @CallSuper
    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        nextListener?.onScrollStateChanged(view, scrollState)
    }

    fun getNextListener(): MultiOnScrollListener<*>? = nextListener

    fun setNextListener(nextListener: MultiOnScrollListener<*>?) {
        this.nextListener = nextListener
    }

    fun addNextListener(nextListener: MultiOnScrollListener<*>): T {
        if (this.nextListener == null) {
            this.nextListener = nextListener
        } else {
            this.nextListener?.addNextListener(nextListener)
        }
        return this as T
    }

}