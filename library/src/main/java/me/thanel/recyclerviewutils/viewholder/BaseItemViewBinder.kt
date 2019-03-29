package me.thanel.recyclerviewutils.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import me.drakeet.multitype.ItemViewBinder
import me.thanel.recyclerviewutils.R

abstract class BaseItemViewBinder<T>(
    @LayoutRes private val layoutResource: Int
) : ItemViewBinder<T, ContainerViewHolder>() {

    var onItemClickListener: ((View, T) -> Unit)? = null

    @CallSuper
    open fun onInflateViewHolder(holder: ContainerViewHolder) {
        if (onItemClickListener == null) return

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(it, getBoundItem(it))
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) =
        ContainerViewHolder(inflater.inflate(layoutResource, parent, false))
            .also(::onInflateViewHolder)

    @CallSuper
    override fun onBindViewHolder(holder: ContainerViewHolder, item: T) {
        holder.itemView.setTag(R.id.bound_item, item)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getBoundItem(view: View) = view.getTag(R.id.bound_item) as? T
        ?: throw IllegalStateException(
            "Bound item not found. Did you forget to call `super.onBindViewHolder`?"
        )
}
