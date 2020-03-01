/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.adapter

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [PostAdaper].
 *
 * @see BaseViewState
 */
sealed class PostAdapterState(
    val hasExtraRow: Boolean
) : BaseViewState {
    /**
     * Listed the added post into list.
     */
    object Added : PostAdapterState(hasExtraRow = false)

    /**
     * Loading for new post to add into list.
     */
    object AddLoading : PostAdapterState(hasExtraRow = false)

    /**
     * Error on add new posts into list.
     */
    object AddError : PostAdapterState(hasExtraRow = false)

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
}
