package es.chewiegames.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun storeUserInDatabase(user: FirebaseUser)
    fun checkUserLogin() : Flow<Boolean>
}