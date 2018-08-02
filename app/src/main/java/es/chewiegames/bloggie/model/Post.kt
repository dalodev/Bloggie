package es.chewiegames.bloggie.model

import java.io.Serializable
import java.util.*

data class Post(
        var id: String? = null,
        var title : String? = null,
        var titleImage : String? = null,
        var content : ArrayList<PostContent> = ArrayList(),
        var comments: ArrayList<Comment> = ArrayList(),
        var littlePoints: Int = -1,
        var views : Int = -1,
        var createdDate : Date? = null,
        var user : User? =null) : Serializable