package es.chewiegames.bloggie.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.david.pokeapp.livedata.BaseSingleLiveEvent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.RC_SIGN_IN
import es.chewiegames.domain.callbacks.OnLaunchResult
import es.chewiegames.domain.callbacks.OnLoginFinishedListener
import es.chewiegames.domain.usecases.LoginUseCase

class LoginViewModel(private val loginUseCase : LoginUseCase) : ViewModel(), OnLaunchResult, OnLoginFinishedListener {

    val goToMainActivity: BaseSingleLiveEvent<Any?> by lazy { BaseSingleLiveEvent<Any?>() }
    val onSuccess: BaseSingleLiveEvent<Any?> by lazy { BaseSingleLiveEvent<Any?>() }
    val showLoginButton: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showMessage: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val startActivityForResult: BaseSingleLiveEvent<Intent> by lazy { BaseSingleLiveEvent<Intent>() }
    val showLoading: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }

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
        loginUseCase.handleLaunch(this)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGN_IN){
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                loginUseCase.storeUserInDatabase(user, this)
            }else{
                if(response == null){
                    showMessage.value = R.string.sign_in_canceled
                    return
                }

                when(response.error!!.errorCode){
                    ErrorCodes.NO_NETWORK -> {
                        showMessage.value = R.string.no_internet_connection
                    }
                    ErrorCodes.UNKNOWN_ERROR -> {
                        showMessage.value = R.string.unknown_error
                    }
                }
                showLoginButton.value = true
            }
        }else{
            showLoginButton.value = true
        }
    }

    override fun userLogged() {
        onSuccess.call()
    }

    override fun userNotLogged() {
        showLoginButton.value = true
    }

    override fun onError(message: String) {
    }

    override fun onSuccess() {
        goToMainActivity.call()
    }

    override fun showProgressDialog() {
        showLoading.value = true
    }

    override fun hideProgressDialog() {
        showLoading.value = false
    }
}