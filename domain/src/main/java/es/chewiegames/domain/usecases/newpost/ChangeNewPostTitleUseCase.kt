package es.chewiegames.domain.usecases.newpost

import es.chewiegames.data.repositoryImpl.NewPostDataRepository
import es.chewiegames.domain.usecases.UseCase

class ChangeNewPostTitleUseCase(private val repository: NewPostDataRepository) : UseCase<Unit, String>() {

    override fun runInBackground(params: String) {
        repository.storePostTitle(params)
    }
}