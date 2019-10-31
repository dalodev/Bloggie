package es.chewiegames.data.model

import java.io.Serializable
import java.util.*

data class PostData(
        var id: String? = null,
        var title : String? = null,
        var titleImage : String? = null,
        var content : ArrayList<PostContentData> = ArrayList(),
        var comments: ArrayList<Comment> = ArrayList(),
        var littlePoints: Int = -1,
        var views : Int = -1,
        var createdDate : Date? = null,
        var userData : UserData? =null) : Serializable