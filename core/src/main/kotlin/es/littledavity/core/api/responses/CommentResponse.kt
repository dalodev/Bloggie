package es.littledavity.core.api.responses

/**
 * Comments Firebase database response item.
 *
 * @param comment The string comment.
 * @param replies The list of replies to specific comment.
 * @param likes The likes of comment.
 * @param user User who posted the comment.
 * @param viewType The type of view of comment.
 */
data class CommentResponse(
    val comment: String,
    val replies: ArrayList<CommentResponse>,
    val likes: Int,
    val user: UserResponse,
    val viewType: Int
)
