/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost

/**
 * Different interaction events for [NewPostViewEvent].
 */
sealed class NewPostViewEvent {

    /**
     * Open feed view.
     */
    object OpenFeed : NewPostViewEvent()
}
