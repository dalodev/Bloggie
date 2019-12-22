/*
 * Copyright 2019 littledavity
 */
package es.littledavity.domain.usecases.user

import com.google.firebase.auth.FirebaseUser
import es.littledavity.data.repository.UserRepository
import es.littledavity.domain.model.User
import es.littledavity.domain.model.mapToUser
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegisterUserUseCase(private val repository: UserRepository) : UseCase<User, FirebaseUser>() {

    override fun runInBackground(params: FirebaseUser): Flow<User> = repository.storeUserInDatabase(params).map { mapToUser(it) }
}
