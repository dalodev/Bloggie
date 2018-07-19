package es.chewiegames.bloggie.ui.login

import es.chewiegames.bloggie.ui.ui.BaseView
import android.content.Intent



interface LoginView : BaseView {
    fun onStartActivityForResult(intent: Intent, request: Int)
    fun navigateToMainActivity()
    fun showLoginButton()
}