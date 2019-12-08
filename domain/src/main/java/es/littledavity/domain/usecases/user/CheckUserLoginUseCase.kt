package es.littledavity.domain.usecases.user

import es.littledavity.data.repository.UserRepository
import es.littledavity.domain.usecases.UseCase
import es.littledavity.domain.usecases.UseCase.None
import kotlinx.coroutines.flow.Flow

class CheckUserLoginUseCase(private val repository: UserRepository) : UseCase<Boolean, None>() {

    override fun runInBackground(params: None): Flow<Boolean> = repository.checkUserLogin()
}
