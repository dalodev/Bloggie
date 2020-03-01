/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import android.graphics.Bitmap
import es.littledavity.core.annotations.OpenForTesting
import java.io.Serializable

/**
 * Model view to display on the screen.
 *
 * @param position position in list
 * @param content text of content
 * @param viewType see TEXT, EDITTEXT, IMAGE
 * @param uriImage uri string of image
 * @param bitmapImage image bitmap
 */
@OpenForTesting
class PostContent(
    var position: Int,
    var content: String? = null,
    var viewType: Int,
    val uriImage: String? = null,
    val bitmapImage: Bitmap? = null
) : Serializable
