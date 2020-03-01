/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.adapter

/**
 * Different states for [FeedAdapter].
 *
 * @see BaseViewState
 */
sealed class FeedAdapterState(
    val hasExtraRow: Boolean
) {

    /**
     * Listed the added post into list.
     */
    object Added : FeedAdapterState(hasExtraRow = true)

    /**
     * Loading for new post to add into list.
     */
    object AddLoading : FeedAdapterState(hasExtraRow = true)

    /**
     * Error on add new posts into list.
     */
    object AddError : FeedAdapterState(hasExtraRow = true)

    /**
     * No more posts to add into list.
     */
    object NoMore : FeedAdapterState(hasExtraRow = false)

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Added].
     *
     * @return True if is added state, otherwise false.
     */
    fun isAdded() = this is Added

    /**
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMore].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMore() = this is NoMore
}
