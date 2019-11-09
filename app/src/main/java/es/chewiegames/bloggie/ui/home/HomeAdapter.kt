package es.chewiegames.bloggie.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import es.chewiegames.domain.model.Post
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import es.chewiegames.data.model.UserData
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.domain.model.User

class HomeAdapter(val context: Context, private val viewModel : HomeViewModel) : RecyclerView.Adapter<HomeViewHolder>() {

    var posts: ArrayList<Post> = viewModel.posts.value ?: arrayListOf()
    private var likedPosts: ArrayList<Post> = arrayListOf()
    private var onBind: Boolean = false

    override fun getItemViewType(position: Int): Int = R.layout.list_item_home

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        onBind = true
        val feedPost: Post = posts[position]
        val feedPostUserData: User = feedPost.user!!
        holder.bind(feedPost, feedPostUserData, viewModel)

        /*

        holder.postTitle.text = feedPost.title
        if (feedPost.titleImage != null) {
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
            mListener.onPostClicked(feedPost, if(feedPost.titleImage!=null) holder.postImage else null, holder.postTitle )
        }

        holder.littlePointButton.setOnCheckedChangeListener { _, b ->
            if(!onBind){
                mListener.onLikePost(posts[holder.adapterPosition], b)
            }
        }

        holder.addComment.setOnClickListener {
            mListener.onAddCommentClicked(posts[holder.adapterPosition])
        }*/
    }

    fun isLikedPost(feedPost: Post): Boolean {
        for (likedPost in likedPosts) {
            if (likedPost.id == feedPost.id) {
                return true
            }
        }
        return false
    }
}