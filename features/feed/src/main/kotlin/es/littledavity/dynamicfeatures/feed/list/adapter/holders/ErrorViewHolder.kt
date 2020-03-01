/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.feed.databinding.ListItemErrorBinding
import es.littledavity.dynamicfeatures.feed.list.FeedViewModel

/**
 * Class describes characters error view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemErrorBinding>(
    binding = ListItemErrorBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel character list view model.
     */
    fun bind(viewModel: FeedViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
