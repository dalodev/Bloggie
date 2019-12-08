package es.littledavity.data.repository

import es.littledavity.data.model.CommentData
import es.littledavity.data.model.PostData
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun storeCommentInDatabase(text: String?, post: PostData): Flow<CommentData>
    fun storeReplyCommentInDatabase(text: String?, post: PostData, parentComment: CommentData): Flow<CommentData>
}
