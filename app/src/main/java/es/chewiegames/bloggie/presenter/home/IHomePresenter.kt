package es.chewiegames.bloggie.presenter.home

import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.presenter.BasePresenter
import es.chewiegames.bloggie.ui.ui.home.HomeView

interface IHomePresenter : BasePresenter<HomeView> {
    abstract fun loadFeedPosts()
    abstract fun onPostLiked(post: Post, checked: Boolean)
}