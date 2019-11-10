package es.chewiegames.bloggie.binding

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.util.BitmapTransform
import es.chewiegames.bloggie.util.MAX_HEIGHT
import es.chewiegames.bloggie.util.MAX_WIDTH
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import es.chewiegames.domain.model.PostContent
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * This method display an image into view with specific size
 * @param imageView the to inflate the image
 * @param imageUri Uri image to display
 * @param size specific size to display image
 */
@BindingAdapter("newPostImageUri", "size")
fun newPostImageDisplay(view: ImageView, imageUri: Uri?, size: Int) {
    if (imageUri != null) {
        Picasso.with(view.context)
                .load(imageUri)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .resize(size, size)
                .centerInside()
                .into(view)
    }
}

/**
 * This method display an image into view with specific size
 *
 */
@BindingAdapter("content", "postContentImageCallback")
fun displayImage(imageView: ImageView, content: PostContent, postContentImageCallback: Callback) {
    val size = ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
    val imgUri: Uri? = Uri.parse(content.uriImage)
    val umgUrl: String? = content.content
    when {
        umgUrl != null -> {
            Picasso.with(imageView.context)
                    .load(umgUrl)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .resize(size, size)
                    .centerInside()
                    .into(imageView, postContentImageCallback)
        }
        imgUri != null -> {
            Picasso.with(imageView.context)
                    .load(imgUri)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .resize(size, size)
                    .centerInside()
                    .into(imageView, postContentImageCallback)
        }
        else -> {
            //TODO display error image
        }
    }
}

@BindingAdapter("setTextContent", "content", "textToAdd")
fun addTextContentToPost(view: FloatingActionButton, viewModel: NewPostViewModel, content: PostContent, textToAdd: EditText) {
    view.setOnClickListener {
        viewModel.doSetTextContent(it, content, textToAdd.text.toString())
    }
}