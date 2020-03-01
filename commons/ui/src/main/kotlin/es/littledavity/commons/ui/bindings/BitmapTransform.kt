/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.bindings

import android.graphics.Bitmap
import coil.bitmappool.BitmapPool
import coil.transform.Transformation

class BitmapTransform(private val maxWidth: Int, private val maxHeight: Int) : Transformation {

    override fun key() = maxWidth.toString() + "x" + maxHeight

    override suspend fun transform(pool: BitmapPool, input: Bitmap): Bitmap {
        val targetWidth: Int
        val targetHeight: Int
        val aspectRatio: Double

        if (input.width > input.height) {
            targetWidth = maxWidth
            aspectRatio = input.height.toDouble() / input.width.toDouble()
            targetHeight = (targetWidth * aspectRatio).toInt()
        } else {
            targetHeight = maxHeight
            aspectRatio = input.width.toDouble() / input.height.toDouble()
            targetWidth = (targetHeight * aspectRatio).toInt()
        }

        val result = Bitmap.createScaledBitmap(input, targetWidth, targetHeight, false)
        if (result != input) {
            input.recycle()
        }
        return result
    }
}
