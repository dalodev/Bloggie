/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.littledavity.bloggie.ui.comments.RepliesCommentsAdapter
import es.littledavity.bloggie.util.RoundedTransformation
import es.littledavity.bloggie.viewmodel.CommentsViewModel
import es.littledavity.domain.model.Comment

@BindingAdapter("displayCommentAvatar")
fun displayCommentAvatar(imageView: ImageView, avatar: String?) {
    avatar?.let {
        Picasso.with(imageView.context).load(avatar).transform(RoundedTransformation(50, 0)).into(imageView)
    }
}

@BindingAdapter("repliesAdapter", "comment", "commentViewModel")
fun setRepliesAdapter(
    recyclerView: RecyclerView,
    replies: ArrayList<Comment>,
    comment: Comment,
    viewModel: CommentsViewModel
) {
    if (replies.isEmpty()) {
        recyclerView.visibility = View.GONE
    } else {
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        val repliesCommentsAdapter = RepliesCommentsAdapter(viewModel)
        recyclerView.adapter = repliesCommentsAdapter
    }
}
