package es.chewiegames.bloggie.ui.ui.home

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.model.Post
import android.support.v7.widget.CardView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.CheckBox
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.model.User
import es.chewiegames.bloggie.util.RoundedTransformation
import kotlinx.android.synthetic.main.list_item_home.view.*
import javax.inject.Inject
import javax.inject.Named

class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface HomeAdapterListener {
        fun onPostClicked(post: Post, vararg viewsToShare: View?)

        fun onLikePost(post: Post, checked: Boolean)

        fun onAddCommentClicked(post: Post)
    }

    @Inject
    lateinit var mUser: User

    @Inject
    lateinit var mListener: HomeAdapterListener

    @Inject
    lateinit var posts: ArrayList<Post>

    @field:[Inject Named("liked posts")]
    lateinit var likedPosts: ArrayList<Post>

    @Inject
    lateinit var context: Context

    private var onBind: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        onBind = true
        val feedPost: Post = posts[position]

        val feedPostUser: User? = feedPost.user

        holder.postTitle.text = feedPost.title
        if (feedPost.titleImage!!.isNotEmpty()) {
            Picasso.with(context)
                    .load(feedPost.titleImage)
                    .into(holder.postImage, object : Callback {
                        override fun onSuccess() {
                            holder.mProgressBarImage.visibility = View.GONE
                            holder.postImage.visibility = View.VISIBLE
                            holder.logoNoImage.visibility = View.GONE
                        }

                        override fun onError() {
                            holder.mProgressBarImage.visibility = View.GONE
                            holder.postImage.visibility = View.GONE
                            holder.logoNoImage.visibility = View.GONE
                        }
                    })
        }else{
            holder.mProgressBarImage.visibility = View.GONE
            holder.logoNoImage.visibility = View.VISIBLE
            holder.postImage.setImageResource(R.drawable.background_splash)
        }

        if(feedPostUser != null){
            Picasso.with(context)
                    .load(feedPostUser.avatar)
                    .transform(RoundedTransformation(50, 0))
                    .into(holder.postProfileImage)
        }

        holder.postLittlePoints.text = feedPost.littlePoints.toString()
        holder.postComments.text = feedPost.comments.size.toString()
        holder.postViews.text = feedPost.views.toString()
        holder.littlePointButton.isChecked = isLikedPost(feedPost)

        onBind = false

        ViewCompat.setTransitionName(holder.postImage, feedPost.id)
        ViewCompat.setTransitionName(holder.postTitle, feedPost.title)

        holder.cardView.setOnClickListener {
            //view.findNavController().navigate(R.id.action_home_to_detail)
            mListener.onPostClicked(feedPost, if(feedPost.titleImage!!.isNotEmpty()) holder.postImage else null, holder.postTitle )
        }

        holder.littlePointButton.setOnCheckedChangeListener { _, b ->
            if(!onBind){
                mListener.onLikePost(posts[holder.adapterPosition], b)
            }
        }

        holder.addComment.setOnClickListener {
            mListener.onAddCommentClicked(posts[holder.adapterPosition])
        }
    }

    fun isLikedPost(feedPost: Post): Boolean {
        for (likedPost in likedPosts) {
            if (likedPost.id == feedPost.id) {
                return true
            }
        }
        return false
    }

    class HomeViewHolder constructor(rootView: View?) : RecyclerView.ViewHolder(rootView) {

        var postImage: ImageView = rootView!!.postImage
        var postTitle: TextView = rootView!!.postTitle
        var postComments: TextView = rootView!!.commentsCount
        var postProfileImage: ImageView = rootView!!.profilePostImageView
        var logoNoImage: ImageView = rootView!!.logoNoImage
        var littlePointButton: CheckBox = rootView!!.littlePoint
        var addComment: ImageView = rootView!!.comments
        var postLittlePoints: TextView = rootView!!.littlePointCount
        var postViews: TextView = rootView!!.viewsCount
        var mProgressBarImage: ProgressBar = rootView!!.imageViewProgressBar
        var cardView: CardView = rootView!!.cardView

    }
}