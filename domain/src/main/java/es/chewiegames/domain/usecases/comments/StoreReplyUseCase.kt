package es.chewiegames.domain.usecases.comments

import es.chewiegames.data.repository.CommentsRepository
import es.chewiegames.domain.model.Comment
import es.chewiegames.domain.model.CommentParams
import es.chewiegames.domain.model.mapToComment
import es.chewiegames.domain.model.mapToPostData
import es.chewiegames.domain.model.mapToCommentData
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreReplyUseCase(private val repository: CommentsRepository) : UseCase<Comment, CommentParams>() {

    override fun runInBackground(params: CommentParams): Flow<Comment> =
            repository.storeReplyCommentInDatabase(params.commentText, mapToPostData(params.post), mapToCommentData(params.parentComment!!))
                    .map {
                        mapToComment(it)
                    }
}
