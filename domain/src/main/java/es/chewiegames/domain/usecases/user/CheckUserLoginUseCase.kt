package es.chewiegames.domain.usecases.user

import es.chewiegames.data.repositoryImpl.UserDataRepository
import es.chewiegames.domain.usecases.UseCase
import es.chewiegames.domain.usecases.UseCase.None

class CheckUserLoginUseCase(private val repository: UserDataRepository) : UseCase<Unit, None>() {

    override fun runInBackground(params: None) {
        repository.checkUserLogin()
    }
}