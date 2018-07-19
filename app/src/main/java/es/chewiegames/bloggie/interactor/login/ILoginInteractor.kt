package es.chewiegames.bloggie.interactor.login

import es.chewiegames.bloggie.interactor.BaseInteractor
import com.google.firebase.auth.FirebaseUser



interface ILoginInteractor : BaseInteractor {

    interface OnLoginFinishedListener {
        fun onError(message: String)

        fun onSuccess()

        fun showProgressDialog()

        fun hideProgressDialog()
    }

    interface OnLaunchResult {
        fun userLogged()
        fun userNotLogged()
    }

    fun handleLaunch(listener: OnLaunchResult)
    fun storeUserInDatabase(user: FirebaseUser, listener: OnLoginFinishedListener)
}