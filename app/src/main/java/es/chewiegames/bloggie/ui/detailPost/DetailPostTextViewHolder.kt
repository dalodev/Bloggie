package es.chewiegames.bloggie.ui.detailPost

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.BR
import es.chewiegames.bloggie.viewmodel.DetailPostViewModel
import es.chewiegames.domain.model.PostContent

class DetailPostTextViewHolder constructor(private val binding: ViewDataBinding, isDetailPost: Boolean) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: PostContent, viewModel: DetailPostViewModel, adapterPosition: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.content, content)
        binding.setVariable(BR.adapterPosition, adapterPosition)
        binding.executePendingBindings()
    }
}
