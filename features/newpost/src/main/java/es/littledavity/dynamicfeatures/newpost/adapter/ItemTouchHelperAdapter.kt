/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
}
