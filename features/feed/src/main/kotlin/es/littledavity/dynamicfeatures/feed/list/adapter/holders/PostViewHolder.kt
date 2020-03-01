/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.domain.model.Post
import es.littledavity.dynamicfeatures.feed.databinding.ListItemPostBinding
import es.littledavity.dynamicfeatures.feed.list.FeedViewModel

/**
 * Class describes post view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class PostViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemPostBinding>(
    binding = ListItemPostBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Character list view model.
     * @param item Character list item.
     */
    fun bind(viewModel: FeedViewModel, item: Post) {
        binding.viewModel = viewModel
        binding.post = item
        binding.executePendingBindings()
    }
}
