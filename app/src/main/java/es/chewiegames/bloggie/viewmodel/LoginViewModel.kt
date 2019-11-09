package es.chewiegames.bloggie.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.*
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.RC_SIGN_IN
import es.chewiegames.domain.usecases.UseCase.None
import es.chewiegames.domain.usecases.user.CheckUserLoginUseCase
import es.chewiegames.domain.usecases.user.RegisterUserUseCase

class LoginViewModel(private val checkUserLoginUseCase: CheckUserLoginUseCase, private val registerUserUseCase : RegisterUserUseCase) : ViewModel() {

    val goToMainActivity: BaseSingleLiveEvent<Any?> by lazy { BaseSingleLiveEvent<Any?>() }
    val loginButtonVisibility: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val loadingVisibility: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val message: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val error: BaseSingleLiveEvent<String> by lazy { BaseSingleLiveEvent<String>() }
    val startActivityForResult: BaseSingleLiveEvent<Intent> by lazy { BaseSingleLiveEvent<Intent>() }
    val userLogged: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }

    fun checkFirebaseAuth() {
        // Choose authentication providers
        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
        val authIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(true)
                .setLogo(R.mipmap.ic_launcher)      // Set logo drawable
                .setTheme(R.style.SplashTheme)      // Set theme
                .build()
        startActivityForResult.value = authIntent
    }

    fun checkForUserLogin() {
        showProgressDialog()
        checkUserLoginUseCase.executeAsync(viewModelScope, None(), onResult = {
            userLogged(it)
        }, onError = {userNotLogged()})
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGN_IN){
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
//                registerUserUseCase.executeAsync(viewModelScope, user, onResult = {onRegisterUserSucces()}, onError = ::onError)
            }else{
                if(response == null){
                    message.value = R.string.sign_in_canceled
                    return
                }

                when(response.error!!.errorCode){
                    ErrorCodes.NO_NETWORK -> {
                        message.value = R.string.no_internet_connection
                    }
                    ErrorCodes.UNKNOWN_ERROR -> {
                        message.value = R.string.unknown_error
                    }
                }
                loginButtonVisibility.value = View.VISIBLE
            }
        }else{
            loginButtonVisibility.value = View.VISIBLE
        }
    }

    private fun userLogged(logged: Boolean) {
        if(logged) goToMainActivity.call()
        else userNotLogged()
        hideProgressDialog()
    }

    private fun userNotLogged() {
        loginButtonVisibility.value = View.VISIBLE
        hideProgressDialog()
    }

    private fun onError(t: Throwable) {
        hideProgressDialog()
        error.value = t.message
    }

    private fun onRegisterUserSucces() {
        goToMainActivity.call()
    }

    private fun showProgressDialog() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun hideProgressDialog() {
        loadingVisibility.value = View.GONE
    }
}