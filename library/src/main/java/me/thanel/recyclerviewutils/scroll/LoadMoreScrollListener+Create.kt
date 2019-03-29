package me.thanel.recyclerviewutils.scroll

fun LoadMoreScrollListener.Companion.createWithLoadMoreAction(
    visibilityThreshold: Int = 10,
    loadMoreAction: () -> Unit
): LoadMoreScrollListener = object : LoadMoreScrollListener() {
    override fun loadMore() = loadMoreAction()

    override val visibilityThreshold = visibilityThreshold
}
