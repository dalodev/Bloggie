/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list

import es.littledavity.domain.model.Post

/**
 * Different interaction events for [FeedFragment].
 */
sealed class FeedViewEvent {

    /**
     * Open character detail view.
     *
     * @param post Character identifier
     */
    data class OpenPostDetail(val post: Post) : FeedViewEvent()
}
