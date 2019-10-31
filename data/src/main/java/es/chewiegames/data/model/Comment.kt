package es.chewiegames.data.model

import java.io.Serializable

data class Comment(
        var comment: String? = null,
        var replies: ArrayList<Comment> = ArrayList(),
        var likes: Int = 0,
        var userData: UserData? = null,
        var viewType: Int? = -1): Serializable