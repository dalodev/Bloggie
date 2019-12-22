/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.repository

import com.google.firebase.auth.FirebaseUser
import es.littledavity.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun storeUserInDatabase(user: FirebaseUser): Flow<UserData>
    fun checkUserLogin(): Flow<Boolean>
}
