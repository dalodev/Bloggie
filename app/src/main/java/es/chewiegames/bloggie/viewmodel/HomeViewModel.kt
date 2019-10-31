package es.chewiegames.bloggie.viewmodel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModel
import com.david.pokeapp.livedata.BaseSingleLiveEvent
import es.chewiegames.domain.model.Post
import es.chewiegames.bloggie.ui.detailPost.DetailPostActivity
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.usecases.feedpost.FeedPostUseCase
import es.chewiegames.domain.usecases.feedpost.UpdateLikedPostUseCase
import java.util.ArrayList

class HomeViewModel(val activity: Activity,
                    private val feedPostUseCase : FeedPostUseCase,
                    private val updateLikedPostUseCase : UpdateLikedPostUseCase)  : ViewModel(), OnLoadFeedPostListener {

    val posts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }
    val likedPosts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }
    val showEmptyView: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showLoading: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val addItem: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val removeItemPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val updateItemPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val goToDetailPostActivity: BaseSingleLiveEvent<HashMap<Intent, Bundle>> by lazy { BaseSingleLiveEvent<HashMap<Intent, Bundle>>() }
    val goToComments: BaseSingleLiveEvent<Bundle> by lazy { BaseSingleLiveEvent<Bundle>() }
    val handleError: BaseSingleLiveEvent<String> by lazy { BaseSingleLiveEvent<String>() }

    var onBind = false

    fun loadFeedPosts() {
        showProgressDialog()
        feedPostUseCase.executeAsync(this, onSuccess = {hideProgressDialog()}, onError = ::onError)
    }

    fun onLikedPost(post: Post, checked: Boolean) {
        updateLikedPostUseCase.checked = checked
        updateLikedPostUseCase.executeAsync(post, onSuccess = {}, onError = {})
    }

    /**
     * trigger when user touch on item of the list
     */
    fun onPostClicked(post: Post, vararg viewsToShare: View?) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_POST, post)
        val p2 = Pair.create(viewsToShare[1], ViewCompat.getTransitionName(viewsToShare[1]!!))
        val options= if(viewsToShare[0] != null){
            val p1 = Pair.create(viewsToShare[0], ViewCompat.getTransitionName(viewsToShare[0]!!))
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2)
        }else{
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p2)

        }
        val intent = Intent(activity, DetailPostActivity::class.java)
        intent.putExtra(EXTRA_POST, post)
        val directionData  = HashMap<Intent, Bundle>()
        directionData[intent] = options.toBundle()!!
        goToDetailPostActivity.value = directionData
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

    fun showProgressDialog() {
        showLoading.value = true
    }

    fun hideProgressDialog() {
        showLoading.value = false
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
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemAdded(post: Post) {
        this.posts.value?.add(post)
        addItem.call()
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemRemoved(position: Int) {
        posts.value?.removeAt(position)
        removeItemPosition.value = position
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemChange(position: Int, post: Post) {
        this.posts.value!![position] = post
        updateItemPosition.value = position
        showEmptyView.call()
        hideProgressDialog()
    }
}