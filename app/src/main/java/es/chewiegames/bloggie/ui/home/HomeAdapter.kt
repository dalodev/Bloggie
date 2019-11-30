package es.chewiegames.bloggie.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.User
import kotlinx.android.synthetic.main.list_item_home.view.*

class HomeAdapter(val context: Context, private val viewModel: HomeViewModel) : RecyclerView.Adapter<HomeViewHolder>() {

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
        viewModel.onAddCommentClicked(holder.itemView.comments, holder.adapterPosition)
        holder.bind(feedPost, feedPostUserData, viewModel)
    }
}
