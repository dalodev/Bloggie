package es.chewiegames.data.repository

import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    // CRUD
    fun getFeedPosts(): Flow<ArrayList<PostData>>
    fun getLikedPostsByUser(): Flow<ArrayList<PostData>>
    fun updateLikedPosts(postData: PostData, checked: Boolean): Flow<PostData>
    // Subscribe
    fun subscribeFeedPosts(callback: OnLoadFeedPostCallback)
}
