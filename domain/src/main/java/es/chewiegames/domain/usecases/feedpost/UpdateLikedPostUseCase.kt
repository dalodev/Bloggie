package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.repository.PostRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.PostParams
import es.chewiegames.domain.model.mapToPost
import es.chewiegames.domain.model.mapToPostData
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateLikedPostUseCase(private val repository: PostRepository) : UseCase<Post, PostParams>() {

    override fun runInBackground(params: PostParams): Flow<Post> =
            repository.updateLikedPosts(
                    mapToPostData(params.post),
                    params.checked).map{ mapToPost(it)}
}