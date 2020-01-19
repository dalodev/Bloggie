/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.login

import android.app.Activity
import android.content.Intent
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.littledavity.core.api.repositories.UserRepository
import es.littledavity.core.utils.RC_SIGN_IN
import es.littledavity.dynamicfeatures.splash.model.User
import es.littledavity.dynamicfeatures.splash.model.UserMapper
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * View model responsible for preparing and managing the data for [LoginFragment].
 *
 * @see ViewModel
 */
@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val userRepository: UserRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val userMapper: UserMapper
) : ViewModel() {

    private val _authIntent = MutableLiveData<Intent>()
    val authIntent: LiveData<Intent>
        get() = _authIntent

    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState>
        get() = _state

    init {
        _state.postValue(LoginViewState.GetStarted)
    }

    fun checkFirebaseAuth() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val authIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            /*.setLogo(R.mipmap.ic_launcher) // Set logo drawable*/
            .setTheme(R.style.LoginTheme) // Set theme
            .build()
        _authIntent.postValue(authIntent)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                viewModelScope.launch {
                    userRepository.storeUser(user)
                        .onStart { _state.postValue(LoginViewState.Loading) }
                        .onEach {
                            userRegistered(userMapper.map(it))
                        }
                        .catch { onError(it) }
                        .launchIn(this)
                }
            } else {
                if (response == null) {
                    _state.postValue(LoginViewState.Canceled)
                    return
                }

                when (response.error!!.errorCode) {
                    ErrorCodes.NO_NETWORK -> {
                        _state.postValue(LoginViewState.NoInternet)
                    }
                    ErrorCodes.UNKNOWN_ERROR -> {
                        _state.postValue(LoginViewState.UnknownError)
                    }
                }
            }
        } else {
            _state.postValue(LoginViewState.UnknownError)
        }
    }

    private fun onError(t: Throwable) {
        _state.postValue(LoginViewState.Error)
    }

    private fun userRegistered(user: User) = _state.postValue(LoginViewState.SuccessLogin)
}
