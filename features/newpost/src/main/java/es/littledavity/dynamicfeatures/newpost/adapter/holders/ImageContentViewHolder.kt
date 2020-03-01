/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.domain.model.PostContent
import es.littledavity.dynamicfeatures.newpost.NewPostViewModel
import es.littledavity.dynamicfeatures.newpost.databinding.ListItemImageContentBinding

/**
 * Class describes post view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ImageContentViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemImageContentBinding>(
    binding = ListItemImageContentBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Character list view model.
     * @param item Character list item.
     */
    fun bind(viewModel: NewPostViewModel, item: PostContent) {
        binding.viewModel = viewModel
        binding.content = item
        binding.executePendingBindings()
    }
}
