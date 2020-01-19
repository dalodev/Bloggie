/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.splash.SplashFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [SplashModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [SplashModule::class],
    dependencies = [CoreComponent::class])
interface SplashComponent {

    /**
     * Inject dependencies on component.
     *
     * @param splashFragment Home component.
     */
    fun inject(splashFragment: SplashFragment)
}
