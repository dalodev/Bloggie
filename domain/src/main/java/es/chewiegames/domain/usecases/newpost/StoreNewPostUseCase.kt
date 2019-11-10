package es.chewiegames.domain.usecases.newpost

import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.domain.model.*
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreNewPostUseCase(private val repository: NewPostRepository) : UseCase<Post, StoreNewPostParams>() {

    override fun runInBackground(params: StoreNewPostParams): Flow<Post> = repository.storeNewPost(
            mapToPostData(params.post), mapPostContentList(params.postContent), params.blogImageView
    ).map { mapToPost(it) }
}