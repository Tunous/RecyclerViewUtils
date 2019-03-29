package me.thanel.recyclerviewutils.diff

import androidx.recyclerview.widget.DiffUtil

inline fun <reified T : Any> ItemCallbacks.register(callback: DiffUtil.ItemCallback<T>) {
    register(T::class, callback)
}
