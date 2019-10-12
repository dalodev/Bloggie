package es.chewiegames.data.repository

import es.chewiegames.data.model.Post
import es.chewiegames.data.callbacks.OnLoadFinishedListener

interface HomeRepository {

    fun loadFeedPostsFromDatabase(listener: OnLoadFinishedListener)
    fun storePostInDatabase(post: Post, listener: OnLoadFinishedListener)
    fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadFinishedListener)
    fun getLikedPostByUser(id: String, listener: OnLoadFinishedListener): Boolean
}