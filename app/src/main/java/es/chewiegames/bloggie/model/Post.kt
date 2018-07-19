package es.chewiegames.bloggie.model

import java.io.Serializable
import java.util.*

data class Post(
        var id: String = "",
        var title : String = "",
        var titleImage : String = "",
        var content : ArrayList<PostContent> = ArrayList(),
        var comments: ArrayList<Comment> = ArrayList(),
        var littlePoints: Int = -1,
        var views : Int = -1,
        var createdDate : Date = Date(),
        var user : User = User()) : Serializable