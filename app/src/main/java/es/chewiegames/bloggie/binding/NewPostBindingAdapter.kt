package es.chewiegames.bloggie.binding

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.util.BitmapTransform
import es.chewiegames.bloggie.util.MAX_HEIGHT
import es.chewiegames.bloggie.util.MAX_WIDTH
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * This method display an image into view with specific size
 * @param imageView the to inflate the image
 * @param imageUri Uri image to display
 * @param size specific size to display image
 */
@BindingAdapter("newPostImageUri", "size")
fun newPostImageDisplay(view: ImageView, imageUri: Uri, size: Int) {
    Picasso.with(view.context)
            .load(imageUri)
            .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
            .resize(size, size)
            .centerInside()
            .into(view)
}

/**
 * This method display an image into view with specific size
 *
 * @param imageView the view
 * @param imageUri Uri image to display
 */
@BindingAdapter("displayForegroundView", "displayProgressBar", "displayImageUri", "displayUrlImage")
fun displayImage(imageView: ImageView, foregroundView : View, displayProgressBar: ProgressBar, imageUri: Uri?, urlImage: String?) {
    val size = ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
    imageView.apply {
        when {
            urlImage != null -> Picasso.with(context)
                    .load(urlImage)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .resize(size, size)
                    .centerInside()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            displayProgressBar.visibility = View.GONE
                            foregroundView.visibility = View.VISIBLE
                        }

                        override fun onError() {
                            displayProgressBar.visibility = View.GONE
                            foregroundView.visibility = View.VISIBLE
                        }
                    })
            imageUri != null -> Picasso.with(context)
                    .load(imageUri)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .resize(size, size)
                    .centerInside()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            displayProgressBar.visibility = View.GONE
                            foregroundView.visibility = View.VISIBLE
                        }

                        override fun onError() {
                            displayProgressBar.visibility = View.GONE
                            foregroundView.visibility = View.VISIBLE
                        }
                    })
            else -> {
                //TODO display error image
            }
        }
    }
}