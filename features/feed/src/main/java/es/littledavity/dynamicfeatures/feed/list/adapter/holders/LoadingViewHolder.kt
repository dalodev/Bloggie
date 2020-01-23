package es.littledavity.dynamicfeatures.feed.list.adapter.holders

import android.view.LayoutInflater
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.feed.databinding.ListItemLoadingBinding

/**
 * Class describes characters loading view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class LoadingViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemLoadingBinding>(
    binding = ListItemLoadingBinding.inflate(inflater)
)
