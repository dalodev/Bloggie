package es.chewiegames.bloggie.ui.newPost

import android.support.v7.widget.RecyclerView


interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
}