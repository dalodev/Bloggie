package es.littledavity.data.repositoryImpl

import com.google.firebase.database.DatabaseReference
import es.littledavity.data.model.CommentData
import es.littledavity.data.model.PostData
import es.littledavity.data.model.UserData
import es.littledavity.data.repository.CommentsRepository
import es.littledavity.data.utils.COMMENT_VIEW
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class CommentsDataRepository(
    private val mDatabasePostByUser: DatabaseReference,
    private val mDatabaseAllPost: DatabaseReference,
    private val mUserData: UserData
) : CommentsRepository {

    override fun storeCommentInDatabase(text: String?, post: PostData): Flow<CommentData> = callbackFlow {
        val comment = CommentData()
        comment.comment = text
        comment.likes = 0
        comment.replies = ArrayList()
        comment.userData = mUserData
        comment.viewType = COMMENT_VIEW
        post.comments.add(comment)
        mDatabaseAllPost.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
        offer(comment)
        awaitClose()
    }

    override fun storeReplyCommentInDatabase(
        text: String?,
        post: PostData,
        parentComment: CommentData
    ): Flow<CommentData> = callbackFlow {
        val comment = CommentData()
        comment.comment = text
        comment.likes = 0
        comment.userData = mUserData
        comment.viewType = COMMENT_VIEW
        parentComment.replies.add(comment)
        post.comments.add(parentComment)
        mDatabaseAllPost.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
        offer(parentComment)
        awaitClose()
    }
}
