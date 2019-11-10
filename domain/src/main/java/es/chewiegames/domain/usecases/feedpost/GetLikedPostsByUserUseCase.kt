package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.repository.PostRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.mapToPosts
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLikedPostsByUserUseCase(private val repository: PostRepository) : UseCase<ArrayList<Post>, UseCase.None>() {
    override fun runInBackground(params: None): Flow<ArrayList<Post>> = repository.getLikedPostsByUser().map{ mapToPosts(it) }
}