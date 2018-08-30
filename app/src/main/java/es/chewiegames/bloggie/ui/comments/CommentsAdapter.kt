package es.chewiegames.bloggie.ui.comments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.util.COMMENT_VIEW
import es.chewiegames.bloggie.util.REPLY_COMMENT
import javax.inject.Inject

class CommentsAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var context : Context

    var comments: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            COMMENT_VIEW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
                CommentViewHolder(view)
            }
            REPLY_COMMENT ->{
                val view = LayoutInflater.from(context).inflate(R.layout.reply_comment_item, parent, false)
                CommentViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
                CommentViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        (holder as CommentViewHolder).comment!!.text = comment.comment
    }

    fun addComment(comment: Comment){
        comments.add(comment)
        notifyItemInserted(comments.size)
    }
}