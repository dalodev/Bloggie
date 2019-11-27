package es.chewiegames.domain.usecases.newpost

import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.StoreNewPostParams
import es.chewiegames.domain.model.mapPostContentList
import es.chewiegames.domain.model.mapToPost
import es.chewiegames.domain.model.mapToPostData
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreNewPostUseCase(private val repository: NewPostRepository) : UseCase<Post, StoreNewPostParams>() {

    override fun runInBackground(params: StoreNewPostParams): Flow<Post> = repository.storeNewPost(
            mapToPostData(params.post), mapPostContentList(params.postContent), params.blogImageView
    ).map { mapToPost(it) }
}
