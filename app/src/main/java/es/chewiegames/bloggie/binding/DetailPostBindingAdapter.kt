package es.chewiegames.bloggie.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.util.BitmapTransform
import es.chewiegames.bloggie.util.MAX_HEIGHT
import es.chewiegames.bloggie.util.MAX_WIDTH
import es.chewiegames.domain.model.PostContent
import kotlin.math.ceil
import kotlin.math.sqrt

@BindingAdapter("displayTitleImage")
fun displayTitleImage(imageView: ImageView, titleImage: String?) {
    Picasso.with(imageView.context).load(titleImage).into(imageView)
}

@BindingAdapter("displayDetailImage")
fun displayDetailImage(imageView: ImageView, content: PostContent?) {
    content?.let {
        val size = ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
        val imgUrl: String? = content.content
        imgUrl?.let {
            Picasso.with(imageView.context)
                    .load(it)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .resize(size, size)
                    .centerInside()
                    .into(imageView)
        }
    }
}