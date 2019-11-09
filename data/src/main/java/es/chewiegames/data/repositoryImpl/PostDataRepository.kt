package es.chewiegames.data.repositoryImpl

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.PostRepository
import kotlin.collections.ArrayList

class PostDataRepository(val mDatabasePostByUser: DatabaseReference,
                         val mDatabaseAllPost: DatabaseReference,
                         val mDatabaseLikedPostByUser: DatabaseReference,
                         val likedPosts: ArrayList<PostData>,
                         val posts: ArrayList<PostData>,
                         val mUserData: UserData) : PostRepository {

    override fun loadFeedPost(callback : OnLoadFeedPostCallback) {
        try {
            getLikedPostByUser(mUserData.id!!)
            mDatabaseAllPost.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    val post: PostData? = dataSnapshot.getValue(PostData::class.java)
                    if (post != null) {
                        if (posts.isEmpty()) {
                            val feedPostUser = post.userData
                            if (feedPostUser != null) {
                                if (feedPostUser.id.equals(mUserData.id)) {
                                    posts.add(post)
                                    callback.onItemAdded(post)
                                } else {
                                    //TODO check follow people and check posts
                                }
                            }
                        } else if (!postsContainPost(posts, post)) {
                            val feedPostUser = post.userData
                            if (feedPostUser != null) {
                                if (feedPostUser.id == mUserData.id) {
                                    posts.add(0, post)
                                    callback.onItemAdded(post)
                                } else {
                                    //TODO check follow people and check posts
                                }
                            }
                        }
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                    val post: PostData? = dataSnapshot.getValue(PostData::class.java)
                    for (i in posts.indices) {
                        if (post!!.id == posts[i].id) {
                            posts[i] = post
                            callback.onItemChange(i, post)
                        }
                    }
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    val key = dataSnapshot.key
                    var position = 0
                    for (post in posts) {
                        if (key == post.id) {
                            posts.remove(post)
                            callback.onItemRemoved(position)
                            break
                        }
                        position++
                    }
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                }
            })
        } catch (e: Exception) {
            throw Exception()
        }
    }

    fun storePost(post: PostData) {
        try {
            mDatabaseAllPost.child(post.id!!).setValue(post)
            mDatabasePostByUser.child(post.id!!).setValue(post)
        } catch (e: Exception) {
            throw Exception()
        }
    }

    override fun updateLikedPost(post: PostData, checked: Boolean) {
        try {
            if (checked) { //add
                mDatabaseLikedPostByUser.child(post.id!!).setValue(post)
            } else { // remove
                mDatabaseLikedPostByUser.child(post.id!!).removeValue()
            }
        } catch (e: Exception) {
            throw Exception()
        }
    }

    override fun getLikedPostByUser(id: String): Boolean {
        try {
            mDatabaseLikedPostByUser.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    val post: PostData? = dataSnapshot.getValue(PostData::class.java)
                    if (post != null) {
                        if (likedPosts.isEmpty()) {
                            val feedPostUserData: UserData? = post.userData
                            if (feedPostUserData != null) {
                                if (feedPostUserData.id == mUserData.id) {
                                    post.littlePoints = post.littlePoints + 1
                                    likedPosts.add(post)
                                    storePost(post)
                                }
                            }
                        } else if (!postsContainPost(likedPosts, post)) {
                            val feedPostUSer: UserData? = post.userData
                            if (feedPostUSer != null) {
                                if (feedPostUSer.id == mUserData.id) {
                                    post.littlePoints = post.littlePoints + 1
                                    likedPosts.add(0, post)
                                    storePost(post)
                                }
                            }
                        }
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    val key: String = dataSnapshot.key!!
                    for (post in likedPosts) {
                        if (key == post.id) {
                            post.littlePoints = post.littlePoints - 1
                            likedPosts.remove(post) //?????????
                            storePost(post)
                            break
                        }
                    }
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                }
            })
        } catch (e: Exception) {
            throw Exception()
        }
        return false
    }

    internal fun postsContainPost(posts: ArrayList<PostData>, post: PostData): Boolean {
        for (p in posts) {
            if (p.id == post.id) {
                return true
            }
        }
        return false
    }
}