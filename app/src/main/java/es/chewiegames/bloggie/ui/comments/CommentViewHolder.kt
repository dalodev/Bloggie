package es.chewiegames.bloggie.ui.comments

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder constructor(rootView: View) : RecyclerView.ViewHolder(rootView) {

    var comment: TextView = rootView.userCommentText
    var userImage: ImageView = rootView.userInputImage
    var timeAgo: TextView = rootView.timeAgo
    var reply: TextView? = rootView.replyButton
    var replysView: LinearLayout? = rootView.replysContainer
    var replysRecyclerView: RecyclerView? = rootView.replysRecyclerView
    var root: ConstraintLayout = rootView.commentItemRoot as ConstraintLayout
}
