/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import es.littledavity.core.annotations.OpenForTesting
import java.util.Date
import kotlin.collections.ArrayList

/**
 * Model view to display on the screen.
 *
 * @param id The unique ID of the post.
 * @param title The title of the post.
 * @param titleImage The title image of the post.
 * @param content The list of content of the post.
 * @param comments The list of comments of the post.
 * @param littlePoints The post liked count.
 * @param views The views count.
 * @param createdDate Date who was created.
 * @param user user who posted content.
 */
@OpenForTesting
data class Post(
    val id: String = "",
    var title: String = "",
    val titleImage: String = "",
    val content: ArrayList<PostContent> = arrayListOf(),
    val comments: ArrayList<Comment> = arrayListOf(),
    val littlePoints: Int = -1,
    val views: Int = 0,
    val createdDate: Date? = null,
    val user: User = User()
)
