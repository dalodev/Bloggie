package es.chewiegames.bloggie.presenter.home

import es.chewiegames.bloggie.interactor.home.HomeInteractor
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.ui.home.HomeView
import javax.inject.Inject

class HomePresenter @Inject constructor(): IHomePresenter {

    @Inject
    lateinit var mHomeInteractor: HomeInteractor

    private var view: HomeView? = null

    override fun setView(view: HomeView) {
        this.view = view
    }

    override fun loadFeedPosts() {
    }

    override fun onPostLiked(post: Post, checked: Boolean) {
    }
}