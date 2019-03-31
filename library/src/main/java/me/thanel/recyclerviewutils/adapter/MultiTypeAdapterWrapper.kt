package me.thanel.recyclerviewutils.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.MultiTypeAdapter
import me.thanel.recyclerviewutils.diff.ItemCallbacks
import me.thanel.recyclerviewutils.diff.ListDiffUtilCallback
import me.thanel.recyclerviewutils.diff.register

/**
 * Wrapper class for [MultiTypeAdapter] that provides support for diff calculation of adapter
 * items.
 */
class MultiTypeAdapterWrapper {
    private val mutex = Mutex()

    val adapter = MultiTypeAdapter()
    val itemCallbacks = ItemCallbacks()

    inline fun <reified T : Any, VH : RecyclerView.ViewHolder> register(
        binder: ItemViewBinder<T, VH>,
        diffCallback: DiffUtil.ItemCallback<T>
    ) {
        adapter.register(binder)
        itemCallbacks.register(diffCallback)
    }

    suspend fun updateItems(
        newItems: List<Any>,
        detectMoves: Boolean = false
    ) = withContext(Dispatchers.Main) {
        // Calculate diff in a background thread
        val diffResult = withContext(Dispatchers.IO) {
            // Use a mutex to prevent multiple calls of this function from modifying items at
            // the same time. We lock the mutex before attempting to access the items.
            mutex.lock(this)

            val oldItems = adapter.items
            val callback = ListDiffUtilCallback(oldItems, newItems, itemCallbacks)
            DiffUtil.calculateDiff(callback, detectMoves)
        }

        adapter.items = newItems
        diffResult.dispatchUpdatesTo(adapter)

        // Unlock mutex after adapter has received the newly loaded data and all updates from the
        // diff calculation have completed.
        mutex.unlock()
    }
}
