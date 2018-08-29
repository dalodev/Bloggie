package es.chewiegames.bloggie.presenter.login

import es.chewiegames.bloggie.presenter.BasePresenter
import es.chewiegames.bloggie.ui.login.LoginView
import android.content.Intent

interface ILoginPresenter : BasePresenter<LoginView> {
    fun checkFirebaseAuth()
    fun checkForUserLogin()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}