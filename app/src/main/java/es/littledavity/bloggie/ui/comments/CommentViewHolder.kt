/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.ui.comments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.bloggie.BR
import es.littledavity.bloggie.viewmodel.CommentsViewModel
import es.littledavity.domain.model.Comment

class CommentViewHolder constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment, viewModel: CommentsViewModel) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.comment, comment)
        binding.executePendingBindings()
    }
}
