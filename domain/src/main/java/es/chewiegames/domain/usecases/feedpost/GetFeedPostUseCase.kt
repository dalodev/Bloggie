package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.repository.PostRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.mapToPosts
import es.chewiegames.domain.usecases.UseCase
import es.chewiegames.domain.usecases.UseCase.None
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedPostUseCase(private val repository: PostRepository) : UseCase<ArrayList<Post> , None>() {
    override fun runInBackground(params: None): Flow<ArrayList<Post>> = repository.getFeedPosts().map{mapToPosts(it)}
}