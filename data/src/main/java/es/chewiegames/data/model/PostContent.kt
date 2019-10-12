package es.chewiegames.data.model

import android.graphics.Bitmap
import java.io.Serializable

data class PostContent(
        var position: Int = -1,
        var content: String? = null,
        var viewType: Int = -1,
        var uriImage: String? = null,
        var bitmapImage: Bitmap? = null) : Serializable