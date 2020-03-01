/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.api.NetworkState
import es.littledavity.domain.model.Post
import es.littledavity.dynamicfeatures.feed.list.paging.FeedPageDataSourceFactory
import es.littledavity.dynamicfeatures.feed.list.paging.PAGE_MAX_ELEMENTS
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val dataSourceFactory: FeedPageDataSourceFactory
) : ViewModel() {

    @VisibleForTesting(otherwise = PRIVATE)
    val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }
    // create PagedList Config
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(PAGE_MAX_ELEMENTS)
        .setPageSize(PAGE_MAX_ELEMENTS).build()

    val event = SingleLiveData<FeedViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, config).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    FeedViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    FeedViewState.Empty
                } else {
                    FeedViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    FeedViewState.AddLoading
                } else {
                    FeedViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    FeedViewState.AddError
                } else {
                    FeedViewState.Error
                }
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Refresh feed fetch them again and update the list.
     */
    fun refreshLoadedFeed() {
        dataSourceFactory.refresh()
    }

    /**
     * Retry last fetch operation to add feed into list.
     */
    fun retryAddFeed() {
        dataSourceFactory.retry()
    }

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param postId Character item.
     */
    fun openPostDetail(post: Post) {
        event.postValue(FeedViewEvent.OpenPostDetail(post))
    }
}
