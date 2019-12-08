package es.littledavity.bloggie.ui.detailPost

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.bloggie.BR
import es.littledavity.bloggie.viewmodel.DetailPostViewModel
import es.littledavity.domain.model.PostContent

class DetailPostImageViewHolder constructor(private val binding: ViewDataBinding, isDetailPost: Boolean) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: PostContent, viewModel: DetailPostViewModel) {
        binding.setVariable(BR.postContent, content)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }
}
