/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.core.utils.EDITTEXT_VIEW
import es.littledavity.core.utils.IMAGE_VIEW
import es.littledavity.core.utils.TEXT_VIEW
import es.littledavity.domain.model.PostContent
import es.littledavity.dynamicfeatures.newpost.NewPostViewModel
import es.littledavity.dynamicfeatures.newpost.adapter.holders.ErrorViewHolder
import es.littledavity.dynamicfeatures.newpost.adapter.holders.ImageContentViewHolder
import es.littledavity.dynamicfeatures.newpost.adapter.holders.LoadingViewHolder
import es.littledavity.dynamicfeatures.newpost.adapter.holders.TextContentViewHolder
import java.util.Collections
import kotlin.collections.ArrayList

/**
 * Enum class containing the different type of cell view, with the configuration.
 */
internal enum class ItemView(val type: Int) {
    TEXT(type = 0),
    IMAGE(type = 1),
    LOADING(type = 2),
    ERROR(type = 3);

    companion object {
        fun valueOf(type: Int): ItemView? = values().first { it.type == type }
    }
}

/**
 * Class for presenting characters List data in a [RecyclerView], including computing
 * diffs between Lists on a background thread.
 *
 */
class PostAdapter(
    private val viewModel: NewPostViewModel,
    private var postContent: ArrayList<PostContent> = viewModel.data.value?.content ?: arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var state: PostAdapterState = PostAdapterState.Added

    /**
     * Return the stable ID for the item at position.
     *
     * @param position Adapter position to query.
     * @return The stable ID of the item at position.
     */
    override fun getItemId(position: Int) =
        when (getItemView(position)) {
            ItemView.TEXT,
            ItemView.IMAGE -> -1L
            ItemView.LOADING -> 0L
            ItemView.ERROR -> 1L
        }

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to
     * represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ItemView.valueOf(viewType)) {
            ItemView.TEXT -> TextContentViewHolder(inflater)
            ItemView.IMAGE -> ImageContentViewHolder(inflater)
            ItemView.LOADING -> LoadingViewHolder(inflater)
            else -> ErrorViewHolder(inflater)
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemView(position)) {
            ItemView.TEXT ->
                getItem(position).let {
                    if (holder is TextContentViewHolder) {
                        holder.bind(viewModel, it)
                    }
                }
            ItemView.IMAGE ->
                getItem(position).let {
                    if (holder is ImageContentViewHolder) {
                        holder.bind(viewModel, it)
                    }
                }
            ItemView.ERROR ->
                if (holder is ErrorViewHolder) {
                    holder.bind(viewModel)
                }
            else -> {
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() =
        if (state.hasExtraRow) {
            postContent.size + 1
        } else {
            postContent.size
        }

    // ============================================================================================
    //  Private setups methods
    // ============================================================================================

    /**
     * Obtain the type of view by the item position.
     *
     * @param position Current item position.
     * @return ItemView type.
     */
    private fun getItemView(position: Int) =
        if (state.hasExtraRow && position == itemCount - 1) {
            if (state.isAddError()) {
                ItemView.ERROR
            } else {
                ItemView.LOADING
            }
        } else {
            when (getItem(position).viewType) {
                TEXT_VIEW -> ItemView.TEXT
                EDITTEXT_VIEW -> ItemView.TEXT
                IMAGE_VIEW -> ItemView.IMAGE
                else -> ItemView.TEXT
            }
        }

    private fun getItem(position: Int) = postContent[position]

    fun restoreItem(item: PostContent) {
        val position = item.position
        postContent.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        viewModel.data.value?.content?.let { it[fromPosition].position = toPosition }
        viewModel.data.value?.content?.let { it[toPosition].position = fromPosition }
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(postContent, i, i + 1)
                viewModel.data.value?.content?.let { it[i].position = i + 1 }
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(postContent, i, i - 1)
                viewModel.data.value?.content?.let { it[i].position = i - 1 }
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val deletedItem: PostContent = postContent[viewHolder.adapterPosition]
        val deletedIndex: Int = viewHolder.adapterPosition
        removeItem(deletedIndex)
        viewModel.itemSwiped(deletedItem, deletedIndex)
    }
    private fun removeItem(position: Int) {
        postContent.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
    }
}
