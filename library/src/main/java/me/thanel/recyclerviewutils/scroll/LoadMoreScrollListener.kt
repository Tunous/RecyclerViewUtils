package me.thanel.recyclerviewutils.scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreScrollListener : RecyclerView.OnScrollListener() {

    open val visibilityThreshold: Int = 10

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            ?: throw IllegalStateException("RecyclerView's layout manager must be LinearLayoutManager")

        // We include 0 in the below operation to also listen to calls from layout
        // calculation which provide 0 to both arguments.
        val isScrollingDown = dy >= 0
        if (!isScrollingDown) {
            return
        }

        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val endThreshold = layoutManager.itemCount - visibilityThreshold
        if (lastVisibleItemPosition >= endThreshold) {
            loadMore()
        }
    }

    abstract fun loadMore()

    companion object
}
