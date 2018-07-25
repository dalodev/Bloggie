package es.chewiegames.bloggie.ui.ui.home

import android.content.Context
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
import es.chewiegames.bloggie.model.User
import kotlinx.android.synthetic.main.list_item_home.view.*
import javax.inject.Inject
import javax.inject.Named


class HomeAdapter @Inject constructor(): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface HomeAdapterListener {
        fun onPostClicked(post: Post, vararg viewsToShare: View)

        fun onLikePost(post: Post, checked: Boolean)

        fun onAddCommentClicked(post: Post)
    }

    @Inject
    lateinit var mUser: User

    @Inject
    lateinit var mListener: HomeAdapterListener

    @Inject
    lateinit var posts : ArrayList<Post>

    @Inject
    @Named("liked posts")
    lateinit var likedPosts : ArrayList<Post>

    @Inject
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val feedPost : Post = posts[position]

        var feedPostUser : User = feedPost.user

        holder.postTitle!!.text = feedPost.title

    }

    fun isLikedPost(feedPost : Post) : Boolean{
        for (likedPost in likedPosts){
            if(likedPost.id == feedPost.id){
                return true
            }
        }
        return false
    }

    class HomeViewHolder constructor(rootView: View) : RecyclerView.ViewHolder(rootView) {

        val postImage: ImageView? = rootView.postImage
        val postTitle: TextView? = rootView.postTitle
        val postComments: TextView? = rootView.commentsCount
        val postProfileImage: ImageView? = rootView.profilePostImageView
        val logoNoImage: ImageView? = rootView.logoNoImage
        val littlePointButton: CheckBox? = rootView.littlePoint
        val addComment: ImageView? = rootView.comments
        val postLittlePoints: TextView? = rootView.littlePointCount
        val postViews: TextView? = rootView.viewsCount
        val mProgressBarImage: ProgressBar? = rootView.imageViewProgressBar
        val cardView: CardView? = rootView.cardView

    }
}