package es.chewiegames.domain.usecases.post

import es.chewiegames.data.repository.PostRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.mapToPostData
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePostUseCase constructor(private val repository: PostRepository) : UseCase<Unit, Post>()  {

    override fun runInBackground(params: Post) : Flow<Unit> = flow {
        repository.updatePostViews(mapToPostData(params))
    }
}