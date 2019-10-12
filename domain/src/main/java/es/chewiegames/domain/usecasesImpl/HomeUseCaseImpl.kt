package es.chewiegames.domain.usecasesImpl

import es.chewiegames.data.callbacks.OnLoadFinishedListener
import es.chewiegames.data.repository.HomeRepository
import es.chewiegames.domain.callbacks.OnLoadHomeFinishedListener
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.usecases.HomeUseCase
import java.util.ArrayList

class HomeUseCaseImpl(private val homeRepository: HomeRepository) : HomeUseCase, OnLoadFinishedListener {

    lateinit var listener: OnLoadHomeFinishedListener

    override fun loadFeedPostsFromDatabase(listener: OnLoadHomeFinishedListener) {
        this.listener = listener
        homeRepository.loadFeedPostsFromDatabase(this)
    }

    override fun storePostInDatabase(post: Post, listener: OnLoadHomeFinishedListener) {
        this.listener = listener
        homeRepository.storePostInDatabase(post as es.chewiegames.data.model.Post, this)
    }

    override fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadHomeFinishedListener) {
        this.listener = listener
        homeRepository.handleFromLikedPostByUser(post as es.chewiegames.data.model.Post, checked, this)
    }

    override fun getLikedPostByUser(id: String, listener: OnLoadHomeFinishedListener) : Boolean {
        this.listener = listener
        return homeRepository.getLikedPostByUser(id, this)
    }

    override fun onError(message: String) {
        listener.onError(message)
    }

    override fun onSuccess(posts: ArrayList<es.chewiegames.data.model.Post>) {
        listener.onSuccess(posts as ArrayList<Post>)
    }

    override fun onItemAdded(post: es.chewiegames.data.model.Post) {
        listener.onItemAdded(post as Post)
    }

    override fun onItemRemoved(position: Int) {
        listener.onItemRemoved(position)
    }

    override fun onItemChange(position: Int, post: es.chewiegames.data.model.Post) {
        listener.onItemChange(position, post as Post)
    }

    override fun showProgressDialog() {
        listener.showProgressDialog()
    }

    override fun hideProgressDialog() {
        listener.hideProgressDialog()
    }
}