/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.bindings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.Request
import coil.size.Scale
import coil.transform.CircleCropTransformation
import es.littledavity.commons.ui.R
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.random.Random

const val WIDTH = 1024
const val HEIGHT = 768
/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter("imageUrl", "imagePlaceholder", requireAll = false)
fun ImageView.imageUrl(url: String?, @DrawableRes placeholderId: Int?) {
    load(url) {
        crossfade(true)
        placeholder(placeholderId?.let {
            ContextCompat.getDrawable(context, it)
        } ?: run {
            val placeholdersColors = resources.getStringArray(R.array.placeholders)
            val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
            ColorDrawable(Color.parseColor(placeholderColor))
        })
    }
}

/**
 * Set rounded image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter("roundedImageUrl", "imagePlaceholder", requireAll = false)
fun ImageView.roundedImageUrl(url: String?, @DrawableRes placeholderId: Int?) {
    load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(placeholderId?.let {
            ContextCompat.getDrawable(context, it)
        } ?: run {
            val placeholdersColors = resources.getStringArray(R.array.placeholders)
            val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
            ColorDrawable(Color.parseColor(placeholderColor))
        })
    }
}

/**
 * This method display an image into view with specific size
 *
 * @param imageUri the uri to inflate the image
 * @param imageUri Uri image to display
 * @param size specific size to display image
 */
@BindingAdapter("post", "size", "imagePlaceholder", requireAll = false)
fun ImageView.postTitleImage(imageUri: Uri?, size: Int, @DrawableRes placeholderId: Int? = null) {
    load(imageUri) {
        transformations(BitmapTransform(WIDTH, HEIGHT))
        size(size)
        crossfade(true)
        placeholder(placeholderId?.let {
            ContextCompat.getDrawable(context, it)
        } ?: run {
            val placeholdersColors = resources.getStringArray(R.array.placeholders)
            val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
            ColorDrawable(Color.parseColor(placeholderColor))
        })
    }
}

fun ImageView.contentImage(imgUri: Uri?, imgUrl: String?, postContentImageCallback: Request.Listener? = null) {
    val size = ceil(sqrt((WIDTH * HEIGHT).toDouble())).toInt()
    when {
        imgUrl != null ->
            load(imgUrl) {
                transformations(BitmapTransform(WIDTH, HEIGHT))
                size(size)
                scale(Scale.FIT)
                listener(postContentImageCallback)
            }

        imgUri != null ->
            load(imgUri) {
                transformations(BitmapTransform(WIDTH, HEIGHT))
                size(size)
                scale(Scale.FIT)
                listener(postContentImageCallback)
            }
        else -> {
            // TODO display error image
        }
    }
}
