package es.littledavity.domain.model

import es.littledavity.data.model.CommentData
import java.io.Serializable

data class Comment(
    var comment: String? = null,
    var replies: ArrayList<Comment> = ArrayList(),
    var likes: Int = 0,
    var user: User? = null,
    var viewType: Int? = -1
) : Serializable

data class CommentParams(
    var commentText: String,
    var post: Post,
    var parentComment: Comment?
)

fun mapToComment(comment: CommentData) = Comment(comment.comment, mapToComments(comment.replies), comment.likes, mapToUser(comment.userData!!), comment.viewType)
fun mapToCommentData(comment: Comment) = CommentData(comment.comment, mapToCommentsData(comment.replies), comment.likes, mapToUserData(comment.user!!), comment.viewType)

fun mapToComments(commentsData: ArrayList<CommentData>): ArrayList<Comment> {
    val comments = arrayListOf<Comment>()
    commentsData.map {
        comments.add(Comment(it.comment, mapToComments(it.replies), it.likes, mapToUser(it.userData!!), it.viewType))
    }
    return comments
}

fun mapToCommentsData(comments: ArrayList<Comment>): ArrayList<CommentData> {
    val commentsData = arrayListOf<CommentData>()
    comments.map {
        commentsData.add(CommentData(it.comment, mapToCommentsData(it.replies), it.likes, mapToUserData(it.user!!), it.viewType))
    }
    return commentsData
}
