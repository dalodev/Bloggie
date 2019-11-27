package es.chewiegames.domain.usecases.user

import es.chewiegames.data.repository.UserRepository
import es.chewiegames.domain.usecases.UseCase
import es.chewiegames.domain.usecases.UseCase.None
import kotlinx.coroutines.flow.Flow

class CheckUserLoginUseCase(private val repository: UserRepository) : UseCase<Boolean, None>() {

    override fun runInBackground(params: None): Flow<Boolean> = repository.checkUserLogin()
}
