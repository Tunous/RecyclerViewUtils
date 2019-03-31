package me.thanel.recyclerviewutils.viewholder

import android.view.View
import androidx.annotation.LayoutRes

abstract class SimpleItemViewBinder<T>(
    @LayoutRes private val layoutResource: Int
) : BaseItemViewBinder<T, ContainerViewHolder>(layoutResource) {

    override fun onCreateViewHolder(itemView: View) = ContainerViewHolder(itemView)
}
