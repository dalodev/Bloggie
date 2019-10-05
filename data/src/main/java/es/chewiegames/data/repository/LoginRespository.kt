package es.chewiegames.data.repository

import com.google.firebase.auth.FirebaseUser

interface LoginRespository {

    interface OnLoginFinishedListener {
        fun onError(message: String)

        fun onSuccess()
    }

    interface OnLaunchResult {
        fun userLogged()
        fun userNotLogged()
    }

    fun storeUserInDatabase(user: FirebaseUser, listener: OnLoginFinishedListener)
    fun checkUserLogin(listener: OnLaunchResult)
}