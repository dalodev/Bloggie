package es.chewiegames.data.repository

import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun storeUserInDatabase(user: FirebaseUser): Flow<UserData>
    fun checkUserLogin(): Flow<Boolean>
}
