package es.chewiegames.bloggie.interactor.comments

import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post

interface ICommentsInteractor  {

    interface CommentsListener {
        fun onAdded(comment:Comment)
    }

    fun storeCommentInDatabase(text: String?, post: Post, listener: CommentsListener)
}