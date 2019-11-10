package es.chewiegames.domain.usecases.user

import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.repository.UserRepository
import es.chewiegames.domain.model.User
import es.chewiegames.domain.model.mapToUser
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegisterUserUseCase(private val repository: UserRepository) : UseCase<User, FirebaseUser>() {

    override fun runInBackground(params: FirebaseUser): Flow<User> = repository.storeUserInDatabase(params).map { mapToUser(it) }
}