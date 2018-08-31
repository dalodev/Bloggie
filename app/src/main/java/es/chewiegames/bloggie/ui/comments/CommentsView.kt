package es.chewiegames.bloggie.ui.comments

import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.ui.BaseView

interface CommentsView : BaseView {
    fun fillValues(post: Post)
    fun setAdapter(comments: ArrayList<Comment>)
    fun commentAdded(comment: Comment)
    fun showReplyTo(show: Boolean, replyToText: String)
    fun goBack()
    fun replyCommentAdded(replyComment: Comment)
    fun setReplyCommentsAdapter(parentComment: Comment)
}