package es.chewiegames.bloggie.model

import android.graphics.Bitmap
import java.io.Serializable

data class PostContent(
        var position: Int = -1,
        var content: String = "",
        var viewType: Int = -1,
        var uriImage: String = "",
        var bitmapImage: Bitmap? = null) : Serializable