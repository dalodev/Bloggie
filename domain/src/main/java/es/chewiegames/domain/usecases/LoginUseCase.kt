package es.chewiegames.domain.usecases

import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.repository.LoginRespository
import es.chewiegames.domain.callbacks.OnLaunchResult
import es.chewiegames.domain.callbacks.OnLoginFinishedListener
import es.chewiegames.domain.usecasesImpl.LoginUseCaseImpl

interface LoginUseCase {

    fun handleLaunch(listener: OnLaunchResult)
    fun storeUserInDatabase(user: FirebaseUser, listener: OnLoginFinishedListener)

    companion object {
        fun createInstance(loginRepository: LoginRespository): LoginUseCase = LoginUseCaseImpl(loginRepository)
    }
}