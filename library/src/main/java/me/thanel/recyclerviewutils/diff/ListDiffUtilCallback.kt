package me.thanel.recyclerviewutils.diff

import androidx.recyclerview.widget.DiffUtil

class ListDiffUtilCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>,
    private val callbacks: ItemCallbacks
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        callbacks.areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        callbacks.areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
        callbacks.getChangePayload(oldList[oldItemPosition], newList[newItemPosition])
}
