package me.thanel.recyclerviewutils.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun <T, VH : RecyclerView.ViewHolder> BaseItemViewBinder<T, VH>.withItemClickListener(listener: (View, T) -> Unit) = apply {
    onItemClickListener = listener
}

fun <T, VH : RecyclerView.ViewHolder> BaseItemViewBinder<T, VH>.withItemClickListener(listener: (T) -> Unit) =
    withItemClickListener { _, item ->
        listener(item)
    }
