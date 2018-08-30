package es.chewiegames.bloggie.ui.comments

import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.ui.BaseView

interface CommentsView : BaseView {
    fun fillValues(post: Post)
    fun setAdapter(comments: ArrayList<Comment>)
    fun commentAdded(comment: Comment)
}