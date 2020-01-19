/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [SplashFragment].
 *
 * @see BaseViewState
 */
sealed class SplashViewState : BaseViewState {

    /**
     * Loading character detail info.
     */
    object Loading : SplashViewState()

    /**
     * Logged user detail info.
     */
    object Logged : SplashViewState()

    /**
     * Not logged user.
     */
    object NotLogged : SplashViewState()

    /**
     * Error on loading character detail info.
     */
    object Error : SplashViewState()

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [Logged].
     *
     * @return True if is logged state, otherwise false.
     */
    fun isLogged() = this is Logged

    /**
     * Check if current view state is [NotLogged].
     *
     * @return True if is not logged state, otherwise false.
     */
    fun isNotLogged() = this is NotLogged

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error
}
