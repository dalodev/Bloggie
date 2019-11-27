package es.chewiegames.bloggie.ui.newPost

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.BR
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import es.chewiegames.domain.model.PostContent

class PostTextViewHolder constructor(private val binding: ViewDataBinding, isDetailPost: Boolean) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: PostContent, viewModel: NewPostViewModel, adapterPosition: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.content, content)
        binding.setVariable(BR.adapterPosition, adapterPosition)
        binding.executePendingBindings()
    }
}
