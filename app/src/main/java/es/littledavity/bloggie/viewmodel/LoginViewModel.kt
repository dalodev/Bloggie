package es.littledavity.bloggie.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.littledavity.bloggie.R
import es.littledavity.bloggie.livedata.BaseSingleLiveEvent
import es.littledavity.bloggie.util.RC_SIGN_IN
import es.littledavity.domain.model.User
import es.littledavity.domain.usecases.UseCase.None
import es.littledavity.domain.usecases.user.CheckUserLoginUseCase
import es.littledavity.domain.usecases.user.RegisterUserUseCase

class LoginViewModel(
    private val checkUserLoginUseCase: CheckUserLoginUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {

    val goToMainActivity: BaseSingleLiveEvent<Any?> by lazy { BaseSingleLiveEvent<Any?>() }
    val loginButton: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val startActivityForResult: BaseSingleLiveEvent<Intent> by lazy { BaseSingleLiveEvent<Intent>() }

    init {
        loginButton.value = View.GONE
        checkUserLogin()
    }

    fun checkFirebaseAuth() {
        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
        val authIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(true)
                .setLogo(R.mipmap.ic_launcher) // Set logo drawable
                .setTheme(R.style.SplashTheme) // Set theme
                .build()
        startActivityForResult.value = authIntent
    }

    private fun checkUserLogin() = checkUserLoginUseCase.executeAsync(viewModelScope, None(), ::userLogged, ::onError, ::showProgressDialog, ::hideProgressDialog)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                registerUserUseCase.executeAsync(viewModelScope, user, ::userRegistered, ::onError, ::showProgressDialog, ::hideProgressDialog)
            } else {
                if (response == null) {
                    message.value = R.string.sign_in_canceled
                    return
                }

                when (response.error!!.errorCode) {
                    ErrorCodes.NO_NETWORK -> {
                        message.value = R.string.no_internet_connection
                    }
                    ErrorCodes.UNKNOWN_ERROR -> {
                        message.value = R.string.unknown_error
                    }
                }
                loginButton.value = View.VISIBLE
            }
        } else {
            loginButton.value = View.VISIBLE
        }
    }

    private fun userLogged(logged: Boolean) {
        if (logged) goToMainActivity.call()
        else userNotLogged()
        hideProgressDialog()
    }

    private fun userNotLogged() {
        loginButton.value = View.VISIBLE
    }

    private fun onError(t: Throwable) {
        userNotLogged()
        error.value = t.message
    }

    private fun userRegistered(user: User) = goToMainActivity.call()
}
