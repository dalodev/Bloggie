package es.chewiegames.bloggie.ui.comments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.BR
import es.chewiegames.bloggie.viewmodel.CommentsViewModel
import es.chewiegames.domain.model.Comment

class CommentViewHolder constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment, viewModel: CommentsViewModel) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.comment, comment)
        binding.executePendingBindings()
    }
}
