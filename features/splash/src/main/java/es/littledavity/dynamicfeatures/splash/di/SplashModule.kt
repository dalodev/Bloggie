/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.api.repositories.UserRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.splash.SplashFragment
import es.littledavity.dynamicfeatures.splash.SplashViewModel
import es.littledavity.dynamicfeatures.splash.model.UserMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that contributes to the object graph [SplashComponent].
 *
 * @see Module
 */
@Module
class SplashModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: SplashFragment
) {

    /**
     * Create a provider method binding for [SplashViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @ExperimentalCoroutinesApi
    @Provides
    @FeatureScope
    fun providesSplashViewModel(
        userRepository: UserRepository
    ) = fragment.viewModel {
        SplashViewModel(userRepository = userRepository)
    }

    /**
     * Create a provider method binding for [UserMapper].
     *
     * @return instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesUserMapper() = UserMapper()
}
