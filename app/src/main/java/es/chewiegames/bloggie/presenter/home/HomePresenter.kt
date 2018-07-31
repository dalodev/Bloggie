package es.chewiegames.bloggie.presenter.home

import es.chewiegames.bloggie.interactor.home.HomeInteractor
import es.chewiegames.bloggie.interactor.home.IHomeInteractor
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.ui.home.HomeView
import java.util.ArrayList
import javax.inject.Inject

class HomePresenter @Inject constructor(): IHomePresenter, IHomeInteractor.OnLoadFinishedListener {

    @Inject
    lateinit var mHomeInteractor: HomeInteractor

    private lateinit var view: HomeView

    override fun setView(view: HomeView) {
        this.view = view
    }

    override fun loadFeedPosts() {
        mHomeInteractor.loadFeedPostsFromDatabase(this)
    }

    override fun onPostLiked(post: Post, checked: Boolean) {
        mHomeInteractor.handleFromLikedPostByUser(post, checked,  this)

    }

    //Interactor listener

    override fun onError(message: String) {
    }

    override fun onSuccess(posts: ArrayList<Post>) {
        view.updateFeedPosts(posts)
        view.showEmptyView()
        hideProgressDialog()
    }

    override fun onItemAdded() {
        view.addItem()
        view.showEmptyView()
        hideProgressDialog()
    }

    override fun onItemRemoved(position: Int) {
        view.removeItem(position)
        view.showEmptyView()
        hideProgressDialog()
    }

    override fun onItemChange(position: Int) {
        view.updateItem(position)
        view.showEmptyView()
        hideProgressDialog()
    }

    override fun showProgressDialog() {
        view.showLoading(true)
    }

    override fun hideProgressDialog() {
        view.showLoading(false)
    }
}