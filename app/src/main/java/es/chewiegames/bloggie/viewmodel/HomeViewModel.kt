package es.chewiegames.bloggie.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent
import es.chewiegames.domain.model.Post
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.model.PostParams
import es.chewiegames.domain.usecases.UseCase.None
import es.chewiegames.domain.usecases.feedpost.GetFeedPostUseCase
import es.chewiegames.domain.usecases.feedpost.GetLikedPostsByUserUseCase
import es.chewiegames.domain.usecases.feedpost.SubscribeFeedPostsUseCase
import es.chewiegames.domain.usecases.feedpost.UpdateLikedPostUseCase
import java.util.ArrayList

class HomeViewModel(private val getFeedPostUseCase: GetFeedPostUseCase,
                    private val getLikedPostsByUserUseCase: GetLikedPostsByUserUseCase,
                    private val updateLikedPostUseCase: UpdateLikedPostUseCase,
                    subscribeFeedPostsUseCase: SubscribeFeedPostsUseCase) : BaseViewModel(), OnLoadFeedPostListener {

    val posts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }
    val addItemAdapter: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val removeItemAdapterPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val updateItemAdapterPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val viewsToShare: BaseSingleLiveEvent<ArrayList<View>> by lazy { BaseSingleLiveEvent<ArrayList<View>>() }
    val navigateToDetail: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val navigateToComments: BaseSingleLiveEvent<Bundle> by lazy { BaseSingleLiveEvent<Bundle>() }

    // This LiveData depends on another so we can use a transformation.
    val emptyViewVisibility: LiveData<Int> = Transformations.map(posts){ if(it.isEmpty()) View.VISIBLE else View.GONE}

    private var likedPosts : ArrayList<Post> = arrayListOf()
    var onBind = false
    var options = Bundle()

    init {
        posts.value = arrayListOf()
        loadFeedPosts()
        loadLikedPostsByUser()
        subscribeFeedPostsUseCase.subscribe(viewModelScope, this, onResult = {}, onError = {})
    }

    private fun loadFeedPosts() = getFeedPostUseCase.executeAsync(viewModelScope, None(), ::onLoadFeedPostSuccess, ::onError, ::showProgressDialog, ::hideProgressDialog)

    private fun loadLikedPostsByUser() = getLikedPostsByUserUseCase.executeAsync(viewModelScope, None(), ::onLoadedLikedPostsByUser, ::onError, ::showProgressDialog, ::hideProgressDialog)

    /**
     * trigger when user touch on item of the list
     */
    fun onPostClicked(post: Post, vararg viewsToShare: View?) {
        this.viewsToShare.value = varargAsList(viewsToShare) as ArrayList<View>
        navigateToDetail.value = post
    }

    /**
     * trigger when user touch on like button in post
     */
    fun onLikePost(post: Post, checked: Boolean) = updateLikedPostUseCase.executeAsync(viewModelScope, PostParams(post, checked), ::likedPostUpdated, ::onError, ::showProgressDialog, ::hideProgressDialog)

    /**
     * trigger when user touch on comment buttom
     */
    fun onAddCommentClicked(post: Post) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_POST, post)
        navigateToComments.value = bundle
    }

    private fun likedPostUpdated(post: Post) {
        val postLiked = likedPosts.find { it.id!! == post.id }
        if (postLiked == null) likedPosts.add(post)
        else likedPosts.remove(postLiked)
        val postInFeed = posts.value!!.find { it.id!! == post.id }
        val position = posts.value!!.indexOf(postInFeed)
        posts.value!![position] = post
    }

    private fun onError(t: Throwable) {
        error.value = t.message
    }

    fun isLikedPost(feedPost: Post): Boolean {
        for (likedPost in likedPosts) {
            if (likedPost.id == feedPost.id) {
                return true
            }
        }
        return false
    }

    private fun onLoadFeedPostSuccess(posts: ArrayList<Post>) {
        this.posts.value?.addAll(posts)
        hideProgressDialog()
    }

    private fun onLoadedLikedPostsByUser(posts: ArrayList<Post>) {
        this.likedPosts = posts
    }

    /**
     * OnLoadFeedPostListener methods
     */
    override fun onItemAdded(post: Post) {
        val containPost = posts.value?.any { it.id == post.id }
        takeIf { !containPost!! }?.let {
            posts.value?.add(0, post)
            addItemAdapter.call()
        }
    }

    override fun onItemRemoved(idRemoved: String) {
        val postToRemove = posts.value!!.find { it.id!! == idRemoved }
        if (postToRemove != null) {
            val position = posts.value!!.indexOf(postToRemove)
            posts.value?.remove(postToRemove)
            removeItemAdapterPosition.value = position
        }
    }

    override fun onItemChange(post: Post) {
        val containPost = this.posts.value!!.find { post.id!! == it.id!! }
        val position = this.posts.value!!.indexOf(containPost)
        this.posts.value!![position] = post
        updateItemAdapterPosition.value = position
    }
}

fun <T> varargAsList(vararg ts: T): ArrayList<T>? {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}