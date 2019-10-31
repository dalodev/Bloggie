package es.chewiegames.data.repository

import android.widget.ImageView
import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData

interface PostRepository {
    fun loadFeedPost(callback: OnLoadFeedPostCallback)
    fun updateLikedPost(post: PostData, checked: Boolean)
    fun getLikedPostByUser(id: String): Boolean
}