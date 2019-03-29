package me.thanel.recyclerviewutils.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class ContainerViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    val context: Context get() = containerView.context
}
