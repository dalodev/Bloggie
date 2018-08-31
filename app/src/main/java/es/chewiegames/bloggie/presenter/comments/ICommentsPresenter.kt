package es.chewiegames.bloggie.presenter.comments

import android.os.Bundle
import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.presenter.BasePresenter
import es.chewiegames.bloggie.ui.comments.CommentsView

interface ICommentsPresenter : BasePresenter<CommentsView> {
    fun loadData(extras: Bundle)
    fun sendComment(text: String?)
    fun loadComments()
    fun replyTo(parentComment: Comment)
    fun handleBack()
    fun loadReplyComments(replies: Comment)
}