/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.model

import java.io.Serializable
import java.util.Date
import kotlin.collections.ArrayList

data class PostData(
    var id: String? = null,
    var title: String? = null,
    var titleImage: String? = null,
    var content: ArrayList<PostContentData> = ArrayList(),
    var comments: ArrayList<CommentData> = ArrayList(),
    var littlePoints: Int = -1,
    var views: Int = -1,
    var createdDate: Date? = null,
    var userData: UserData? = null
) : Serializable
