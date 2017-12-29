package moe.feng.common.arch.list.internal

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import moe.feng.common.arch.list.callback.OnListLoadMoreListener

/**
 * A type of RecyclerView OnScrollListener help you to listen load more event
 */
class OnRVLoadMoreListener(private val callback: OnListLoadMoreListener)
	: RecyclerView.OnScrollListener() {

	override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
		if (dy > 0) {
			val visibleItemCount: Int
			val totalItemCount: Int
			val pastVisibleItems: Int

			val lm = recyclerView!!.layoutManager
			when (lm) {
				is LinearLayoutManager -> {
					visibleItemCount = lm.childCount
					totalItemCount = lm.itemCount
					pastVisibleItems = lm.findFirstVisibleItemPosition()
				}
				is GridLayoutManager -> {
					visibleItemCount = lm.childCount
					totalItemCount = lm.itemCount
					pastVisibleItems = lm.findFirstVisibleItemPosition()
				}
				is StaggeredGridLayoutManager -> {
					visibleItemCount = lm.childCount
					totalItemCount = lm.itemCount
					pastVisibleItems = lm.findFirstVisibleItemPositions(IntArray(lm.spanCount))[0]
				}
				else -> throw IllegalStateException(
						"Unsupported layout manager. Please commit issue on github.")
			}

			if (visibleItemCount + pastVisibleItems >= totalItemCount) {
				callback.onLoadMore()
			}
		}
	}

}