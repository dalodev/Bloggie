package es.chewiegames.bloggie.ui.newPost

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import android.widget.ImageView
import kotlinx.android.synthetic.main.imageview_content.view.*

class PostImageViewHolder constructor(rootView: View, isDetailPost: Boolean) : RecyclerView.ViewHolder(rootView) {
    var image: ImageView? = rootView.imageView
    var foregroundView: CardView = rootView.foregroundView
    var mProgressBar: ProgressBar = rootView.progressBar
    var container: View? = rootView.rootView
}