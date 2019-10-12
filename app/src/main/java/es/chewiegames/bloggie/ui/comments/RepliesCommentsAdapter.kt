package es.chewiegames.bloggie.ui.comments

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import es.chewiegames.data.model.Comment

class RepliesCommentsAdapter constructor(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var comments: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reply_comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        (holder as CommentViewHolder).comment.text = comment.comment
    }
}