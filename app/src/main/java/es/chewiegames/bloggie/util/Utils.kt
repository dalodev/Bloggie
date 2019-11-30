package es.chewiegames.bloggie.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * converts drawable to bitmap
 * @param drawable
 * @return bitmap
 */
fun getBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
    } else {
        Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun hideKeyBoard(context: Context, view: View) {
    // hide virtual keyboard
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN)
}
