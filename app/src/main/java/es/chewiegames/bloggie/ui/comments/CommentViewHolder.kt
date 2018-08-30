package es.chewiegames.bloggie.ui.comments

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder constructor(rootView: View) : RecyclerView.ViewHolder(rootView) {

    var comment: TextView? = rootView.userCommentText
}