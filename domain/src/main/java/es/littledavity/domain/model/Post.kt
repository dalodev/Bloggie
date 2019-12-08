package es.littledavity.domain.model

import android.widget.ImageView
import es.littledavity.data.model.PostData
import java.io.Serializable
import java.util.Date
import kotlin.collections.ArrayList

data class Post(
    var id: String? = null,
    var title: String? = null,
    var titleImage: String? = null,
    var content: ArrayList<PostContent> = ArrayList(),
    var comments: ArrayList<Comment> = ArrayList(),
    var littlePoints: Int = -1,
    var views: Int = -1,
    var createdDate: Date? = null,
    var user: User? = null
) : Serializable

data class PostParams(
    var post: Post,
    var checked: Boolean
)

fun mapToPost(post: PostData) = Post(post.id, post.title, post.titleImage, mapPostContentList(post.content), mapToComments(post.comments), post.littlePoints, post.views, post.createdDate, mapToUser(post.userData!!))
fun mapToPostData(post: Post) = PostData(post.id, post.title, post.titleImage, mapPostContentDataList(post.content), mapToCommentsData(post.comments), post.littlePoints, post.views, post.createdDate, post.user?.let { mapToUserData(post.user!!) })
fun mapToPosts(postData: ArrayList<PostData>): ArrayList<Post> {
    val posts = arrayListOf<Post>()
    postData.forEach {
        posts.add(mapToPost(it))
    }
    return posts
}

data class StoreNewPostParams(
    var post: Post,
    var postContent: ArrayList<PostContent>,
    var blogImageView: ImageView
)
