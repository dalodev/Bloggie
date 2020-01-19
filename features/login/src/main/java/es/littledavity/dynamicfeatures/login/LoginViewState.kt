/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.login

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [LoginFragment].
 *
 * @see BaseViewState
 */
sealed class LoginViewState : BaseViewState {

    /**
     * Initial state Login fragment.
     */
    object GetStarted : LoginViewState()

    /**
     * Loading user success.
     */
    object SuccessLogin : LoginViewState()

    /**
     * Loading logging
     */
    object Loading : LoginViewState()

    /**
     * Error on loading logging
     */
    object Error : LoginViewState()

    /**
     * User cancel login
     */
    object Canceled : LoginViewState()

    /**
     * User has no internet connection
     */
    object NoInternet : LoginViewState()

    /**
     * Unknown error
     */
    object UnknownError : LoginViewState()

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error || this is Canceled || this is NoInternet || this is UnknownError

    /**
     * Check if current view state is [GetStarted].
     *
     * @return True if is error state, otherwise false.
     */
    fun isGetStarted() = this is GetStarted
}
