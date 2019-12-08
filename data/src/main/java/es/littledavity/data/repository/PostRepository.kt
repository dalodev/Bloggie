package es.littledavity.data.repository

import es.littledavity.data.callbacks.OnLoadFeedPostCallback
import es.littledavity.data.model.PostData
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    // CRUD
    fun getFeedPosts(): Flow<ArrayList<PostData>>
    fun updatePost(post: PostData)
    fun getLikedPostsByUser(): Flow<ArrayList<PostData>>
    fun updateLikedPosts(postData: PostData, checked: Boolean): Flow<PostData>
    // Subscribe
    fun subscribeFeedPosts(callback: OnLoadFeedPostCallback)
}
