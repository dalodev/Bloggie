package es.littledavity.bloggie.binding

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.littledavity.bloggie.util.RoundedTransformation

@BindingAdapter(value = ["feedPostTitleImage", "progressBarImage", "logoNoImage"], requireAll = true)
fun feedPostTitleImage(
    view: ImageView,
    titleImage: String?,
    progressBarImage: ProgressBar,
    logoNoImage: ImageView
) {
    if (titleImage != null) {
        Picasso.with(view.context)
                .load(titleImage)
                .into(view, object : Callback {
                    override fun onSuccess() {
                        progressBarImage.visibility = View.GONE
                        view.visibility = View.VISIBLE
                        logoNoImage.visibility = View.GONE
                    }

                    override fun onError() {
                        progressBarImage.visibility = View.GONE
                        view.visibility = View.GONE
                        logoNoImage.visibility = View.GONE
                    }
                })
    } else {
        progressBarImage.visibility = View.GONE
        logoNoImage.visibility = View.VISIBLE
        // view.setImageResource(R.drawable.background_splash)
    }
}

@BindingAdapter("profileImage")
fun profileImage(view: ImageView, avatar: String) {
    Picasso.with(view.context)
            .load(avatar)
            .transform(RoundedTransformation(50, 0))
            .into(view)
}
