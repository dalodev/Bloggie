package es.littledavity.domain.usecases.post

import es.littledavity.data.repository.PostRepository
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.mapToPostData
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePostUseCase constructor(private val repository: PostRepository) : UseCase<Unit, Post>() {

    override fun runInBackground(params: Post): Flow<Unit> = flow {
        repository.updatePost(mapToPostData(params))
    }
}
