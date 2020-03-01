/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import es.littledavity.core.annotations.OpenForTesting

/**
 * Model view to display on the screen.
 *
 * @param comment text of comment
 * @param replies array of comments replies
 * @param likes likes of comment
 * @param user user was wrote the comment
 * @param viewType =¿?
 */
@OpenForTesting
data class Comment(
    val comment: String,
    val replies: ArrayList<Comment>,
    val likes: Int,
    val user: User,
    val viewType: Int
)
