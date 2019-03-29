package me.thanel.recyclerviewutils.diff

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

class ItemCallbacks : DiffUtil.ItemCallback<Any>() {

    private val childCallbacks = mutableMapOf<KClass<*>, DiffUtil.ItemCallback<*>>()

    fun <T : Any> register(kClass: KClass<T>, callback: DiffUtil.ItemCallback<T>) {
        unregisterWithWarning(kClass)
        childCallbacks[kClass] = callback
    }

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) return false
        return getItemCallback(newItem::class).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return getItemCallback(newItem::class).areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
        return getItemCallback(newItem::class).getChangePayload(oldItem, newItem)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getItemCallback(kClass: KClass<*>): DiffUtil.ItemCallback<Any> {
        val callback = childCallbacks[kClass]
        if (callback != null) {
            return callback as DiffUtil.ItemCallback<Any>
        }

        val alternativeCallback = childCallbacks.keys.firstOrNull {
            it.java.isAssignableFrom(kClass.java)
        }
        return childCallbacks[alternativeCallback] as? DiffUtil.ItemCallback<Any>
            ?: throw IllegalArgumentException("Missing item callback for class: $kClass")
    }

    private fun unregister(kClass: KClass<*>): Boolean {
        return childCallbacks.remove(kClass) != null
    }

    private fun unregisterWithWarning(kClass: KClass<*>) {
        if (unregister(kClass)) {
            Log.w(
                TAG,
                "The type ${kClass.java.simpleName} you originally registered is now overwritten."
            )
        }
    }

    companion object {
        private const val TAG = "RecyclerViewUtils"
    }
}

