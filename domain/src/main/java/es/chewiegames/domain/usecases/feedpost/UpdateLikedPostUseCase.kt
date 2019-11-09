package es.chewiegames.domain.usecases.feedpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import es.chewiegames.data.model.PostData
import es.chewiegames.data.repository.PostRepository
import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.mapToPostdata
import es.chewiegames.domain.usecases.UseCase
import java.lang.IllegalArgumentException

class UpdateLikedPostUseCase(private val repository : PostRepository) {

    var checked : Boolean = false

}