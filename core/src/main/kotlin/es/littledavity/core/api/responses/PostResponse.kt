/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.responses

import es.littledavity.core.annotations.OpenForTesting
import java.util.Date
import kotlin.collections.ArrayList

/**
 * Post Firebase database response item.
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
data class PostResponse(
    val id: String = "",
    val title: String = "",
    var titleImage: String = "",
    var content: ArrayList<PostContentResponse> = arrayListOf(),
    val comments: ArrayList<CommentResponse> = arrayListOf(),
    val littlePoints: Int = -1,
    val views: Int = -1,
    val createdDate: Date? = Date(),
    val user: UserResponse = UserResponse()
)
