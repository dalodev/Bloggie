package es.chewiegames.bloggie.ui.comments

import es.chewiegames.bloggie.ui.ui.BaseView
import es.chewiegames.data.model.Comment
import es.chewiegames.domain.model.Post

interface CommentsView : BaseView {
    fun fillValues(post: Post)
    fun setAdapter(comments: ArrayList<Comment>)
    fun commentAdded(comment: Comment)
    fun showReplyTo(show: Boolean, replyToText: String)
    fun goBack()
    fun replyCommentAdded(replyComment: Comment)
    fun setReplyCommentsAdapter(parentComment: Comment)
}
