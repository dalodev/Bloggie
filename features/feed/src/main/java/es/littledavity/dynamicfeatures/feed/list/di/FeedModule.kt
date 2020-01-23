/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.api.repositories.PostRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.feed.list.FeedFragment
import es.littledavity.dynamicfeatures.feed.list.FeedViewModel
import es.littledavity.dynamicfeatures.feed.list.adapter.FeedAdapter
import es.littledavity.dynamicfeatures.feed.list.model.PostMapper
import es.littledavity.dynamicfeatures.feed.list.paging.FeedPageDataSource
import es.littledavity.dynamicfeatures.feed.list.paging.FeedPageDataSourceFactory

/**
 * Class that contributes to the object graph [FeedComponent].
 *
 * @see Module
 */
@Module
class FeedModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: FeedFragment
) {

    /**
     * Create a provider method binding for [FeedViewModel].
     *
     * @param dataFactory Data source factory for posts.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesFeedViewModel(
        dataFactory: FeedPageDataSourceFactory
    ) = fragment.viewModel {
        FeedViewModel(dataFactory)
    }

    /**
     * Create a provider method binding for [FeedPageDataSource].
     *
     * @return Instance of data source.
     * @see Provides
     */
    @Provides
    fun providesFeedPageDataSource(
        viewModel: FeedViewModel,
        repository: PostRepository,
        mapper: PostMapper
    ) = FeedPageDataSource(
        repository = repository,
        scope = viewModel.viewModelScope,
        mapper = mapper
    )

    /**
     * Create a provider method binding for [PostMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesPostMapper() = PostMapper()

    /**
     * Create a provider method binding for [FeedAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesFeedAdapter(
        viewModel: FeedViewModel
    ) = FeedAdapter(viewModel)
}
