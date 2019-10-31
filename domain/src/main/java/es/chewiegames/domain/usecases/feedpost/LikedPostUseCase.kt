package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.domain.usecases.UseCase

class LikedPostUseCase(private val repository : PostDataRepository) : UseCase<Unit, String>() {

    override fun runInBackground(params: String) {
        repository.getLikedPostByUser(params)
    }
}