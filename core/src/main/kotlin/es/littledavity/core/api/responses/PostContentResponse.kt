/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.responses

import android.graphics.Bitmap
import es.littledavity.core.annotations.OpenForTesting

/**
 * Post content Firebase database response item.
 *
 * @param position Position saved.
 * @param content The title of the post.
 * @param viewType The type of view (text, image...).
 * @param uriImage The uri image in case of image type.
 * @param bitmapImage The bitmap image in case of image type.
 */
@OpenForTesting
data class PostContentResponse(
    val position: Int,
    val content: String,
    val viewType: Int,
    val uriImage: String,
    val bitmapImage: Bitmap
)
