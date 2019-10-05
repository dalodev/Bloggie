package es.chewiegames.bloggie.interactor.home

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import es.chewiegames.bloggie.model.Post
import es.chewiegames.data.model.User
import javax.inject.Inject
import javax.inject.Named

class HomeInteractor @Inject constructor(): IHomeInteractor {

    @field:[Inject Named("post by user")]
    lateinit var mDatabasePostByUser: DatabaseReference

    @field:[Inject Named("all posts")]
    lateinit var mDatabaseAllPost: DatabaseReference

    @field:[Inject Named("liked posts by user")]
    lateinit var mDatabaseLikedPostByUser: DatabaseReference

    @Inject
    lateinit var mUser: User

    @Inject
    lateinit var posts : ArrayList<Post>

    @field:[Inject Named("liked posts")]
    lateinit var likedPosts : ArrayList<Post>


    override fun loadFeedPostsFromDatabase(listener: IHomeInteractor.OnLoadFinishedListener) {
        getLikedPostByUser(mUser.id!!, listener)
        mDatabaseAllPost.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val post: Post? = dataSnapshot.getValue(Post::class.java)
                if(post != null){
                    if(posts.isEmpty()){
                        val feedPostUser = post.user
                        if(feedPostUser!=null){
                            if(feedPostUser.id.equals(mUser.id)){
                                posts.add(post)
                                listener.onItemAdded()
                            }else{
                                //TODO check follow people and check posts
                            }
                        }
                    }else if (!postsContainPost(posts, post)){
                        val feedPostUser= post.user
                        if(feedPostUser != null){
                            if(feedPostUser.id == mUser.id){
                                posts.add(0, post)
                                listener.onItemAdded()
                            }else{
                                //TODO check follow people and check posts
                            }
                        }
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                val post : Post? = dataSnapshot.getValue(Post::class.java)
                for(i in posts.indices){
                    if(post!!.id == posts[i].id){
                        posts[i] = post
                        listener.onItemChange(i)
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val key  = dataSnapshot.key
                var position = 0
                for (post in posts){
                    if(key == post.id){
                        posts.remove(post)
                        listener.onItemRemoved(position)
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

    }

    override fun storePostInDatabase(post: Post, listener: IHomeInteractor.OnLoadFinishedListener) {
        mDatabaseAllPost.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
    }

    override fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: IHomeInteractor.OnLoadFinishedListener) {
        if (checked){ //add
            mDatabaseLikedPostByUser.child(post.id!!).setValue(post)
        }else{ // remove
            mDatabaseLikedPostByUser.child(post.id!!).removeValue()
        }
    }

    override fun getLikedPostByUser(id: String, listener: IHomeInteractor.OnLoadFinishedListener): Boolean {
        mDatabaseLikedPostByUser.addChildEventListener(object: ChildEventListener{

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val post: Post? = dataSnapshot.getValue(Post::class.java)
                if(post!=null){
                    if(likedPosts.isEmpty()){
                        val feedPostUser : User? = post.user
                        if(feedPostUser!=null){
                            if(feedPostUser.id == mUser.id){
                                post.littlePoints = post.littlePoints+1
                                likedPosts.add(post)
                                storePostInDatabase(post, listener)
                            }
                        }
                    }else if(!postsContainPost(likedPosts, post)){
                        val feedPostUSer : User? = post.user
                        if(feedPostUSer!=null){
                            if(feedPostUSer.id == mUser.id){
                                post.littlePoints = post.littlePoints+1
                                likedPosts.add(0, post)
                                storePostInDatabase(post, listener)
                            }
                        }
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val key : String = dataSnapshot.key!!
                for (post in likedPosts){
                    if(key == post.id){
                        post.littlePoints = post.littlePoints-1
                        likedPosts.remove(post) //?????????
                        storePostInDatabase(post, listener)
                        break
                    }
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
        })
        return false
    }

    internal fun postsContainPost(posts: ArrayList<Post>, post: Post) : Boolean{
        for(p in posts){
            if(p.id == post.id){
                return true
            }
        }
        return false
    }
}