package es.chewiegames.bloggie.interactor.comments

import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post

interface ICommentsInteractor  {

    interface CommentsListener {
        fun onCommentAdded(comment:Comment)
        fun onReplyCommentAdded(replyComment:Comment)
    }

    fun storeCommentInDatabase(text: String?, post: Post, listener: CommentsListener)
    fun storeReplyCommentInDatabase(text: String?, post: Post, parentComment: Comment, listener: CommentsListener)
}