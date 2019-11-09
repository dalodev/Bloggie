package es.chewiegames.domain.model

import es.chewiegames.data.model.Comment
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import java.io.Serializable
import java.util.*

data class Post(
        var id: String? = null,
        var title : String? = null,
        var titleImage : String? = null,
        var content : ArrayList<PostContentData> = ArrayList(),
        var comments: ArrayList<Comment> = ArrayList(),
        var littlePoints: Int = -1,
        var views : Int = -1,
        var createdDate : Date? = null,
        var user : User? =null) : Serializable

fun mapToPost(post: PostData) = Post(post.id, post.title, post.titleImage, post.content, post.comments, post.littlePoints, post.views, post.createdDate, mapToUser(post.userData!!))
fun mapToPostdata(post: Post) = PostData(post.id, post.title, post.titleImage, post.content, post.comments, post.littlePoints, post.views, post.createdDate, mapToUserData(post.user!!))
