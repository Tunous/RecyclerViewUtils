package me.thanel.recyclerviewutils.viewholder

import android.view.View

fun <T> BaseItemViewBinder<T>.withItemClickListener(listener: (View, T) -> Unit) = apply {
    onItemClickListener = listener
}

fun <T> BaseItemViewBinder<T>.withItemClickListener(listener: (T) -> Unit) =
    withItemClickListener { _, item ->
        listener(item)
    }
