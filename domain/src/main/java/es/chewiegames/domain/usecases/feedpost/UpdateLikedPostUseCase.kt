package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.model.PostData
import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.usecases.UseCase
import java.lang.IllegalArgumentException

class UpdateLikedPostUseCase(private val repository : PostDataRepository) : UseCase<Unit, Post>() {

    var checked : Boolean = false

    override fun runInBackground(params: Post) {
        if(params.id !=null) repository.updateLikedPost(params as PostData, checked)
        else throw IllegalArgumentException()
    }
}