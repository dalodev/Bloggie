package es.chewiegames.bloggie.ui.home

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.BR
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.data.model.User
import kotlinx.android.synthetic.main.list_item_home.view.*

class HomeViewHolder constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feedPost: Post, feedPostUser: User, homeViewModel: HomeViewModel) {
        binding.setVariable(BR.homeViewModel, homeViewModel)
        ViewCompat.setTransitionName(binding.root.postImage,  feedPost.id)
        ViewCompat.setTransitionName(binding.root.postTitle,  feedPost.title)
        binding.executePendingBindings()
    }
}