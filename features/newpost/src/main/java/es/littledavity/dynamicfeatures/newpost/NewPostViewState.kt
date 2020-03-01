/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost

import es.littledavity.commons.ui.base.BaseViewState
import es.littledavity.domain.model.PostContent

/**
 * Different states for [NewPostFragment].
 *
 * @see BaseViewState
 */
sealed class NewPostViewState : BaseViewState {

    /**
     * Started posting view.
     */
    object Started : NewPostViewState()

    /**
     * Pick image state from gallry to title
     */
    object PickImageFromGalleryTitle : NewPostViewState()

    /**
     * Pick image state from gallery to content
     */
    data class PickImageFromGalleryContent(val content: PostContent? = null) : NewPostViewState()

    /**
     * Incomplete view state
     */
    object Incomplete : NewPostViewState()

    /**
     * View type state view
     */
    object ImageViewType : NewPostViewState()
    object EditTextViewType : NewPostViewState()

    /**
     * Item deleted state view
     */
    data class ItemDeleted(val postContent: PostContent) : NewPostViewState()

    /**
     * Error view state
     */
    object Error : NewPostViewState()

    /**
     * Check if current view state is [Incomplete]
     *
     * @return True if is incomplete state, otherwise false
     */
    fun isStarted() = this is Started

    /**
     * Check if current view state is [Incomplete]
     *
     * @return True if is incomplete state, otherwise false
     */
    fun isIncomplete() = this is Incomplete || this is Started
}
