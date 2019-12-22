/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.ui.newPost

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.bloggie.BR
import es.littledavity.bloggie.viewmodel.NewPostViewModel
import es.littledavity.domain.model.PostContent

class PostTextViewHolder constructor(private val binding: ViewDataBinding, isDetailPost: Boolean) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: PostContent, viewModel: NewPostViewModel, adapterPosition: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.content, content)
        binding.setVariable(BR.adapterPosition, adapterPosition)
        binding.executePendingBindings()
    }
}
