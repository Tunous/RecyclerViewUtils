package me.thanel.recyclerviewutils.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import me.drakeet.multitype.ItemViewBinder
import me.thanel.recyclerviewutils.R

abstract class BaseItemViewBinder<T, VH : RecyclerView.ViewHolder>(
    @LayoutRes private val layoutResource: Int
) : ItemViewBinder<T, VH>() {

    var onItemClickListener: ((View, T) -> Unit)? = null

    abstract fun onCreateViewHolder(itemView: View): VH

    final override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH =
        onCreateViewHolder(inflater.inflate(layoutResource, parent, false))
            .also(::onInflateViewHolder)

    @CallSuper
    open fun onInflateViewHolder(holder: VH) {
        if (onItemClickListener == null) return

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(it, getBoundItem(it))
        }
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, item: T) {
        holder.itemView.setTag(R.id.bound_item, item)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, item: T, payloads: List<Any>) {
        holder.itemView.setTag(R.id.bound_item, item)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, item)
        }
    }

    protected fun <E : Enum<E>> handleEnumPayloadChanges(payloads: List<Any>, block: (E) -> Unit) {
        for (payload in payloads) {
            @Suppress("UNCHECKED_CAST")
            val changes = payload as? List<E>
            if (changes == null) {
                Log.w("RecyclerViewUtils", "Unsupported payload: $payload")
                continue
            }
            changes.forEach(block)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getBoundItem(view: View) = view.getTag(R.id.bound_item) as? T
        ?: throw IllegalStateException(
            "Bound item not found. Did you forget to call `super.onBindViewHolder`?"
        )
}
