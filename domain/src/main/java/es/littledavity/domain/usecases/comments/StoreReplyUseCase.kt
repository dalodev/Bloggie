package es.littledavity.domain.usecases.comments

import es.littledavity.data.repository.CommentsRepository
import es.littledavity.domain.model.Comment
import es.littledavity.domain.model.CommentParams
import es.littledavity.domain.model.mapToComment
import es.littledavity.domain.model.mapToCommentData
import es.littledavity.domain.model.mapToPostData
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreReplyUseCase(private val repository: CommentsRepository) : UseCase<Comment, CommentParams>() {

    override fun runInBackground(params: CommentParams): Flow<Comment> =
            repository.storeReplyCommentInDatabase(params.commentText, mapToPostData(params.post), mapToCommentData(params.parentComment!!))
                    .map {
                        mapToComment(it)
                    }
}
