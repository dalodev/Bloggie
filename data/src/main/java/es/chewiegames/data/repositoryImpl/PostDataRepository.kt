package es.chewiegames.data.repositoryImpl

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "PostDataRepository"
@ExperimentalCoroutinesApi
class PostDataRepository(
    val mDatabasePostByUser: DatabaseReference,
    val mDatabaseAllPost: DatabaseReference,
    val mDatabaseLikedPostByUser: DatabaseReference,
    val mUserData: UserData
) : PostRepository {

    override fun getFeedPosts(): Flow<ArrayList<PostData>> = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                close(dataSnapshot.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = arrayListOf<PostData>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(PostData::class.java)
                    if (post != null) posts.add(0, post)
                }
                offer(posts)
            }
        }
        mDatabasePostByUser.child(mUserData.id!!).addListenerForSingleValueEvent(eventListener)
        awaitClose { mDatabasePostByUser.removeEventListener(eventListener) }
    }

    override fun getLikedPostsByUser(): Flow<ArrayList<PostData>> = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                close(dataSnapshot.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = arrayListOf<PostData>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(PostData::class.java)
                    if (post != null) posts.add(post)
                }
                offer(posts)
            }
        }
        mDatabaseLikedPostByUser.addListenerForSingleValueEvent(eventListener)
        awaitClose { mDatabaseLikedPostByUser.removeEventListener(eventListener) }
    }

    override fun updateLikedPosts(postData: PostData, checked: Boolean): Flow<PostData> = callbackFlow {
        postData.userData = mUserData
        if (checked) {
            postData.littlePoints += 1
            mDatabaseLikedPostByUser.child(postData.id!!).setValue(postData)
            updatePost(postData)
        } else {
            postData.littlePoints -= 1
            mDatabaseLikedPostByUser.child(postData.id!!).removeValue()
            updatePost(postData)
        }
        offer(postData)
        awaitClose()
    }

    override fun subscribeFeedPosts(callback: OnLoadFeedPostCallback) {
        mDatabasePostByUser.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val post: PostData? = dataSnapshot.getValue(PostData::class.java)
                if (post != null) {
                    val feedPostUser = post.userData
                    if (feedPostUser != null) {
                        if (feedPostUser.id.equals(mUserData.id)) {
                            callback.onItemAdded(post)
                            // TODO define what to do when add a post to feed
                        } else {
                            // TODO check follow people and check posts
                        }
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                val post: PostData? = dataSnapshot.getValue(PostData::class.java)
                if (post != null) callback.onItemChange(post)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val key = dataSnapshot.key
                if (key != null) callback.onItemRemoved(key)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
        })
    }

    override fun updatePost(post: PostData) {
        mDatabaseAllPost.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
    }
}
