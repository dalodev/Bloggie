package es.chewiegames.bloggie.ui.newPost

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.support.v7.widget.CardView
import android.widget.ImageView
import kotlinx.android.synthetic.main.imageview_content.view.*

class PostImageViewHolder constructor(rootView: View, isDetailPost: Boolean) : RecyclerView.ViewHolder(rootView) {
    var image: ImageView? = rootView.imageView
    var foregroundView: CardView = rootView.foregroundView
    var mProgressBar: ProgressBar = rootView.progressBar
    var container: View? = rootView.rootView
}