/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.littledavity.core.api.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * View model responsible for preparing and managing the data for [SplashFragment].
 *
 * @see ViewModel
 */
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableLiveData<SplashViewState>()
    val state: LiveData<SplashViewState>
        get() = _state

    fun checkUserLogin() {
        viewModelScope.launch {
            userRepository.checkUserLogin()
                .onStart { _state.postValue(SplashViewState.Loading) }
                .onEach {
                    delay(1000)
                    userLogged(it) }
                .catch { onError(it) }
                .launchIn(this)
        }
    }

    private fun userLogged(logged: Boolean) {
        if (logged) {
            _state.postValue(SplashViewState.Logged)
        } else userNotLogged()
    }

    private fun userNotLogged() {
        _state.postValue(SplashViewState.NotLogged)
    }

    private fun onError(t: Throwable) {
        _state.postValue(SplashViewState.Error)
    }
}
