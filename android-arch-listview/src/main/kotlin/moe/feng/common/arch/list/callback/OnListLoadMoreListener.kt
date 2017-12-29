package moe.feng.common.arch.list.callback

interface OnListLoadMoreListener {

    /**
     * When RecyclerView should load more contents (Whether or not there is more),
     * onLoadMore() will be called
     */
    fun onLoadMore()

}