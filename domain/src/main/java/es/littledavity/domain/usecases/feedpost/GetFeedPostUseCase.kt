package es.littledavity.domain.usecases.feedpost

import es.littledavity.data.repository.PostRepository
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.mapToPosts
import es.littledavity.domain.usecases.UseCase
import es.littledavity.domain.usecases.UseCase.None
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedPostUseCase(private val repository: PostRepository) : UseCase<ArrayList<Post>, None>() {
    override fun runInBackground(params: None): Flow<ArrayList<Post>> = repository.getFeedPosts().map { mapToPosts(it) }
}
