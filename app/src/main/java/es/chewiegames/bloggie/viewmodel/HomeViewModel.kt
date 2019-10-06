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
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.detailPost.DetailPostActivity
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.data.callbacks.OnLoadFinishedListener
import es.chewiegames.domain.usecases.HomeUseCase
import java.util.ArrayList

class HomeViewModel(val activity: Activity, private val homeUseCase : HomeUseCase)  : ViewModel(), OnLoadFinishedListener {

    val posts: BaseSingleLiveEvent<ArrayList<Post>> by lazy { BaseSingleLiveEvent<ArrayList<Post>>() }
    val showEmptyView: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showLoading: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val addItem: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val removeItemPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val updateItemPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val goToDetailPostActivity: BaseSingleLiveEvent<HashMap<Intent, Bundle>> by lazy { BaseSingleLiveEvent<HashMap<Intent, Bundle>>() }
    val goToComments: BaseSingleLiveEvent<Bundle> by lazy { BaseSingleLiveEvent<Bundle>() }

    fun loadFeedPosts() {
        homeUseCase.loadFeedPostsFromDatabase(this)
    }

    fun onPostLiked(post: Post, checked: Boolean) {
        homeUseCase.handleFromLikedPostByUser(post, checked,  this)
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
        onPostLiked(post, checked)
    }

    /**
     * trigger when user touch on comment buttom
     */
    fun onAddCommentClicked(post: Post) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_POST, post)
        goToComments.value = bundle
    }

    override fun onError(message: String) {
    }

    override fun onSuccess(posts: ArrayList<Post>) {
        this.posts.value = posts
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemAdded() {
        addItem.call()
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemRemoved(position: Int) {
        removeItemPosition.value = position
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun onItemChange(position: Int) {
        updateItemPosition.value = position
        showEmptyView.call()
        hideProgressDialog()
    }

    override fun showProgressDialog() {
        showLoading.value = true
    }

    override fun hideProgressDialog() {
        showLoading.value = false
    }
}