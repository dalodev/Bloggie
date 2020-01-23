package es.littledavity.core.api.repositories

import androidx.annotation.VisibleForTesting
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import es.littledavity.core.api.responses.PostResponse
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Repository module for handling posts data from firebase.
 */
@ExperimentalCoroutinesApi
class PostRepository @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Named("posts")
    internal val mDatabasePosts: DatabaseReference,
    @Named("postsByUser")
    internal val mDatabasePostByUser: DatabaseReference,
    @Named("likedPostsByUser")
    internal val mDatabaseLikedPostByUser: DatabaseReference
) {

    fun getFeedPosts(): Flow<ArrayList<PostResponse>> = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                close(dataSnapshot.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = arrayListOf<PostResponse>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(PostResponse::class.java)
                    if (post != null) posts.add(0, post)
                }
                offer(posts)
            }
        }
        mDatabasePostByUser.addListenerForSingleValueEvent(eventListener)
        awaitClose { mDatabasePostByUser.removeEventListener(eventListener) }
    }

    fun getLikedPostsByUser(): Flow<ArrayList<PostResponse>> = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                close(dataSnapshot.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = arrayListOf<PostResponse>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(PostResponse::class.java)
                    if (post != null) posts.add(post)
                }
                offer(posts)
            }
        }
        mDatabaseLikedPostByUser.addListenerForSingleValueEvent(eventListener)
        awaitClose { mDatabaseLikedPostByUser.removeEventListener(eventListener) }
    }

    fun updateLikedPosts(postData: PostResponse, checked: Boolean): Flow<PostResponse> = callbackFlow {
        if (checked) {
//            postData.littlePoints += 1
            mDatabaseLikedPostByUser.child(postData.id).setValue(postData)
            updatePost(postData)
        } else {
//            postData.littlePoints -= 1
            mDatabaseLikedPostByUser.child(postData.id).removeValue()
            updatePost(postData)
        }
        offer(postData)
        awaitClose()
    }

    private fun updatePost(post: PostResponse) {
        mDatabasePosts.child(post.id).setValue(post)
        mDatabasePostByUser.child(post.id).setValue(post)
    }
}
