/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.paging

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import es.littledavity.dynamicfeatures.feed.list.model.Post
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI.
 *
 * @see DataSource.Factory
 */
@ExperimentalCoroutinesApi
class FeedPageDataSourceFactory
@Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val providerDataSource: Provider<FeedPageDataSource>
) : DataSource.Factory<Int, Post>() {

    var sourceLiveData = MutableLiveData<FeedPageDataSource>()

    /**
     * Create a DataSource.
     *
     * @return The new DataSource.
     * @see DataSource.Factory.create
     */
    override fun create(): DataSource<Int, Post> {
        val dataSource = providerDataSource.get()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    /**
     * Force refresh data source by invalidating and re-create again.
     */
    fun refresh() {
        sourceLiveData.value?.invalidate()
    }

    /**
     * Force retry last fetch operation on data source.
     */
    fun retry() {
        sourceLiveData.value?.retry()
    }
}
