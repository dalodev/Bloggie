/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter.holders

import android.view.LayoutInflater
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.newpost.databinding.ListItemNewPostLoadingBinding

/**
 * Class describes characters loading view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class LoadingViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemNewPostLoadingBinding>(
    binding = ListItemNewPostLoadingBinding.inflate(inflater)
)
