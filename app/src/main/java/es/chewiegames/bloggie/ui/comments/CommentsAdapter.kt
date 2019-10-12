package es.chewiegames.bloggie.ui.comments

import android.animation.LayoutTransition
import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.data.model.Comment
import es.chewiegames.bloggie.util.RoundedTransformation
import javax.inject.Inject

class CommentsAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface CommentsAdapterListener {
        fun replyComment(parentComment: Comment)
        fun showReplies(parentComment: Comment)
    }

    @Inject
    lateinit var context : Context

    @Inject
    lateinit var mListener: CommentsAdapterListener


    var comments: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        (holder as CommentViewHolder).comment.text = comment.comment
        holder.root!!.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        if(comment.user!!.avatar != null) Picasso.with(context).load(comment.user!!.avatar).transform(RoundedTransformation(50, 0)).into(holder.userImage)

        if(comment.replies.isEmpty()){
            holder.replysRecyclerView!!.visibility = View.GONE
        }else{
            holder.replysRecyclerView!!.visibility = View.VISIBLE
            holder.replysRecyclerView!!.layoutManager = LinearLayoutManager(context)
            holder.replysRecyclerView!!.itemAnimator = DefaultItemAnimator()
            var repliesCommentsAdapter = RepliesCommentsAdapter(context)
            repliesCommentsAdapter.comments = comment.replies
            holder.replysRecyclerView!!.adapter = repliesCommentsAdapter
        }

        holder.reply!!.setOnClickListener {
            mListener.replyComment(comments[holder.adapterPosition])
        }
    }

    fun repliesAdded(parentComment: Comment) {
        notifyDataSetChanged()
    }
}