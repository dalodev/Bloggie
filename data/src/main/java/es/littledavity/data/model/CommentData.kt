/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.model

import java.io.Serializable

data class CommentData(
    var comment: String? = null,
    var replies: ArrayList<CommentData> = ArrayList(),
    var likes: Int = 0,
    var userData: UserData? = null,
    var viewType: Int? = -1
) : Serializable
