package es.chewiegames.bloggie.ui.home

import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.BR
import es.chewiegames.domain.model.Post
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.data.model.UserData
import es.chewiegames.domain.model.User
import kotlinx.android.synthetic.main.list_item_home.view.*

class HomeViewHolder constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feedPost: Post, feedPostUserData: User, homeViewModel: HomeViewModel) {
        homeViewModel.onBind = true
        binding.setVariable(BR.homeViewModel, homeViewModel)
        binding.setVariable(BR.feedPost, feedPost)
        binding.setVariable(BR.user, feedPostUserData)
        binding.setVariable(BR.adapterPosition, adapterPosition)
        ViewCompat.setTransitionName(binding.root.postImage,  feedPost.id)
        ViewCompat.setTransitionName(binding.root.postTitle,  feedPost.title)
        binding.executePendingBindings()
        homeViewModel.onBind = false
    }
}