/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.feed.list.FeedFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [FeedModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [FeedModule::class],
    dependencies = [CoreComponent::class])
interface FeedComponent {

    /**
     * Inject dependencies on component.
     *
     * @param feedFragment posts list component.
     */
    fun inject(feedFragment: FeedFragment)
}
