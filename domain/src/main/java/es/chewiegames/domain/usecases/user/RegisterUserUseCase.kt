package es.chewiegames.domain.usecases.user

import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.repository.UserRepository
import es.chewiegames.domain.usecases.UseCase

class RegisterUserUseCase(private val repository: UserRepository) : UseCase<Unit, FirebaseUser>() {

    override fun runInBackground(params: FirebaseUser) {
        repository.storeUserInDatabase(params)
    }
}