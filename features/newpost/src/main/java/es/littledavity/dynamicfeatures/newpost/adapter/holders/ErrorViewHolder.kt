/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.newpost.NewPostViewModel
import es.littledavity.dynamicfeatures.newpost.databinding.ListItemNewPostErrorBinding

/**
 * Class describes characters error view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemNewPostErrorBinding>(
    binding = ListItemNewPostErrorBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel character list view model.
     */
    fun bind(viewModel: NewPostViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
