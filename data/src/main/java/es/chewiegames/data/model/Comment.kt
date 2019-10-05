package es.chewiegames.bloggie.model

import es.chewiegames.data.model.User
import java.io.Serializable

data class Comment(
        var comment: String? = null,
        var replies: ArrayList<Comment> = ArrayList(),
        var likes: Int = 0,
        var user: User? = null,
        var viewType: Int? = -1): Serializable