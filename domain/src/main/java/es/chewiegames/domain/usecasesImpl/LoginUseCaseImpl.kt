package es.chewiegames.domain.usecasesImpl

import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.repository.LoginRespository
import es.chewiegames.domain.callbacks.OnLaunchResult
import es.chewiegames.domain.callbacks.OnLoginFinishedListener
import es.chewiegames.domain.usecases.LoginUseCase

class LoginUseCaseImpl(private val loginRepository : LoginRespository) : LoginUseCase {

    override fun handleLaunch(listener: OnLaunchResult) {
        loginRepository.checkUserLogin(object : LoginRespository.OnLaunchResult {
            override fun userLogged() {
                listener.userLogged()
            }

            override fun userNotLogged() {
                listener.userNotLogged()
            }
        })
    }

    override fun storeUserInDatabase(user: FirebaseUser, listener: OnLoginFinishedListener) {
        loginRepository.storeUserInDatabase(user, object: LoginRespository.OnLoginFinishedListener{
            override fun onError(message: String) {
                listener.onError(message)
            }
            override fun onSuccess() {
                listener.onSuccess()
            }
        })
    }
}