package es.chewiegames.data.repository

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun storeUserInDatabase(user: FirebaseUser)
    fun checkUserLogin() : Boolean
}