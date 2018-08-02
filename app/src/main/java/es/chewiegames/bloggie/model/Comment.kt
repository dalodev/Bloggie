package es.chewiegames.bloggie.model

import java.io.Serializable

data class Comment(
        var comment: String? = null,
        var responses: ArrayList<Comment> = ArrayList(),
        var likes: Int = 0,
        var user: User? = null): Serializable