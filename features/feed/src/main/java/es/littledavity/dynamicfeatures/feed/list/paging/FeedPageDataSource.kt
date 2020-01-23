/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.paging

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import es.littledavity.core.api.NetworkState
import es.littledavity.core.api.repositories.PostRepository
import es.littledavity.dynamicfeatures.feed.list.model.Post
import es.littledavity.dynamicfeatures.feed.list.model.PostMapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

const val PAGE_INIT_ELEMENTS = 0
const val PAGE_MAX_ELEMENTS = 50

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages. Obtaining paginated the posts.
 *
 * @see PageKeyedDataSource
 */
@ExperimentalCoroutinesApi
open class FeedPageDataSource @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val repository: PostRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val scope: CoroutineScope,
    @VisibleForTesting(otherwise = PRIVATE)
    val mapper: PostMapper
) : PageKeyedDataSource<Int, Post>() {

    val networkState = MutableLiveData<NetworkState>()
    @VisibleForTesting(otherwise = PRIVATE)
    var retry: (() -> Unit)? = null

    /**
     * Load initial data.
     *
     * @param params Parameters for initial load, including requested load size.
     * @param callback Callback that receives initial load data.
     * @see PageKeyedDataSource.loadInitial
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Post>
    ) {
        scope.launch(CoroutineExceptionHandler { _, _ ->
            retry = {
                loadInitial(params, callback)
            }
            networkState.postValue(NetworkState.Error())
        }) {
            repository.getFeedPosts(
                offset = PAGE_INIT_ELEMENTS,
                limit = PAGE_MAX_ELEMENTS
            ).onStart { networkState.postValue(NetworkState.Loading()) }
                .onEach {
                    val data = mapper.map(it)
                    callback.onResult(data, null, PAGE_MAX_ELEMENTS)
                    networkState.postValue(NetworkState.Success(isEmptyResponse = data.isEmpty()))
                }.launchIn(this)
        }
    }

    /**
     * Append page with the key specified
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadAfter
     */
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Post>
    ) {
        scope.launch(CoroutineExceptionHandler { _, _ ->
            retry = {
                loadAfter(params, callback)
            }
            networkState.postValue(NetworkState.Error(true))
        }) {
            repository.getFeedPosts(
                offset = params.key,
                limit = PAGE_MAX_ELEMENTS
            ).onStart { networkState.postValue(NetworkState.Loading()) }
                .onEach {
                    val data = mapper.map(it)
                    callback.onResult(data, params.key + PAGE_MAX_ELEMENTS)
                    networkState.postValue(NetworkState.Success(true, data.isEmpty()))
                }.launchIn(this)
        }
    }

    /**
     * Prepend page with the key specified
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadBefore
     */
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Post>
    ) {
        // Ignored, since we only ever append to our initial load
    }

    /**
     * Force retry last fetch operation in case it has ever been previously executed.
     */
    fun retry() {
        retry?.invoke()
    }
}
