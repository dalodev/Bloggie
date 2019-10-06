package es.chewiegames.bloggie.ui.home

import es.chewiegames.bloggie.ui.ui.BaseView
import es.chewiegames.bloggie.model.Post



interface HomeView : BaseView {
    fun updateFeedPosts(posts: ArrayList<Post>)
    fun updateItem(position: Int)
    fun addItem()
    fun removeItem(position: Int)
    fun showEmptyView()
}