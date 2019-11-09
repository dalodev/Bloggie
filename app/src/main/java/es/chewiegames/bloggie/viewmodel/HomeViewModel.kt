package es.chewiegames.bloggie.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent
import es.chewiegames.domain.model.Post
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.usecases.feedpost.FeedPostUseCase
import es.chewiegames.domain.usecases.feedpost.UpdateLikedPostUseCase
import java.util.ArrayList

class HomeViewModel(private val feedPostUseCase : FeedPostUseCase,
                    private val updateLikedPostUseCase : UpdateLikedPostUseCase)  : ViewModel(), OnLoadFeedPostListener {

    val posts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }
    val emptyViewVisibility: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val loadingVisibility: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val addItemAdapter: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val removeItemAdapterPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val updateItemAdapterPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    private val handleError: BaseSingleLiveEvent<String> by lazy { BaseSingleLiveEvent<String>() }
    val viewsToShare: BaseSingleLiveEvent<ArrayList<View>> by lazy { BaseSingleLiveEvent<ArrayList<View>>() }
    val postToDetail: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val goToComments: BaseSingleLiveEvent<Bundle> by lazy { BaseSingleLiveEvent<Bundle>() }
    private val likedPosts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }

    var onBind = false
    var options = Bundle()

    fun loadFeedPosts() {
        showProgressDialog()
//        feedPostUseCase.executeAsync(viewModelScope,this, onResult = {hideProgressDialog()}, onError = ::onError)
    }

    private fun onLikedPost(post: Post, checked: Boolean) {
        updateLikedPostUseCase.checked = checked
//        updateLikedPostUseCase.executeAsync(viewModelScope, post, onResult = {}, onError = {})
    }

    /**
     * trigger when user touch on item of the list
     */
    fun onPostClicked(post: Post, vararg viewsToShare: View?) {
        this.viewsToShare.value = varargAsList(viewsToShare) as ArrayList<View>
        postToDetail.value = post

    }

    /**
     * trigger when user touch on like button in post
     */
    fun onLikePost(post: Post, checked: Boolean) {
        onLikedPost(post, checked)
    }

    /**
     * trigger when user touch on comment buttom
     */
    fun onAddCommentClicked(post: Post) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_POST, post)
        goToComments.value = bundle
    }

    private fun onError(t: Throwable) {
        handleError.value = t.message
    }

    private fun showProgressDialog() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun hideProgressDialog() {
        loadingVisibility.value = View.GONE
    }

    fun isLikedPost(feedPost: Post): Boolean {
        for (likedPost in likedPosts.value!!) {
            if (likedPost.id == feedPost.id) {
                return true
            }
        }
        return false
    }

    /**
     * OnLoadFeedPostListener methods
     */
    override fun onLoadFeedPostSuccess(posts: ArrayList<Post>) {
        this.posts.value = posts
        emptyViewVisibility.call()
        hideProgressDialog()
    }

    override fun onItemAdded(post: Post) {
        this.posts.value?.add(post)
        addItemAdapter.call()
        emptyViewVisibility.call()
        hideProgressDialog()
    }

    override fun onItemRemoved(position: Int) {
        posts.value?.removeAt(position)
        removeItemAdapterPosition.value = position
        emptyViewVisibility.call()
        hideProgressDialog()
    }

    override fun onItemChange(position: Int, post: Post) {
        this.posts.value!![position] = post
        updateItemAdapterPosition.value = position
        emptyViewVisibility.call()
        hideProgressDialog()
    }
}

fun <T> varargAsList(vararg ts: T): ArrayList<T>? {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}