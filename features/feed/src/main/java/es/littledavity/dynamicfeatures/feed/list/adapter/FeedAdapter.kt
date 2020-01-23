/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BasePagedListAdapter
import es.littledavity.dynamicfeatures.feed.list.FeedViewModel
import es.littledavity.dynamicfeatures.feed.list.adapter.holders.LoadingViewHolder
import es.littledavity.dynamicfeatures.feed.list.model.Post

/**
 * Class for presenting characters List data in a [RecyclerView], including computing
 * diffs between Lists on a background thread.
 *
 * @see BaseListAdapter
 */
class FeedAdapter constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val viewModel: FeedViewModel
) : BasePagedListAdapter<Post>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to
     * represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param inflater Instantiates a layout XML file into its corresponding View objects.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see BaseListAdapter.onCreateViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return LoadingViewHolder(inflater)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @see BaseListAdapter.onBindViewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}
