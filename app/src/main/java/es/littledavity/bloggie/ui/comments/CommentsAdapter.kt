package es.littledavity.bloggie.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.bloggie.R
import es.littledavity.bloggie.viewmodel.CommentsViewModel
import es.littledavity.data.model.CommentData
import es.littledavity.domain.model.Comment
import javax.inject.Inject

class CommentsAdapter @Inject constructor(
    private val viewModel: CommentsViewModel,
    val comments: ArrayList<Comment> = viewModel.comments.value ?: arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = R.layout.comment_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        (holder as CommentViewHolder).bind(comment, viewModel)
    }

    fun repliesAdded(parentComment: CommentData) {
        notifyDataSetChanged()
    }
}
