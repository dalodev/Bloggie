/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.newpost.NewPostFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [NewPostModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [NewPostModule::class],
    dependencies = [CoreComponent::class])
interface NewPostComponent {
    /**
     * Inject dependencies on component.
     *
     * @param fragment new post component.
     */
    fun inject(fragment: NewPostFragment)
}
