/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.login.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.login.LoginFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [LoginModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [LoginModule::class],
    dependencies = [CoreComponent::class])
interface LoginComponent {

    /**
     * Inject dependencies on component.
     *
     * @param splashFragment Home component.
     */
    fun inject(loginFragment: LoginFragment)
}
