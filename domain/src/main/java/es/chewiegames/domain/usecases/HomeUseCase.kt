package es.chewiegames.domain.usecases

import es.chewiegames.bloggie.model.Post
import es.chewiegames.data.callbacks.OnLoadFinishedListener
import es.chewiegames.data.repository.HomeRepository
import es.chewiegames.domain.usecasesImpl.HomeUseCaseImpl

interface HomeUseCase {

    fun loadFeedPostsFromDatabase(listener: OnLoadFinishedListener)
    fun storePostInDatabase(post: Post, listener: OnLoadFinishedListener)
    fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadFinishedListener)
    fun getLikedPostByUser(id: String, listener: OnLoadFinishedListener): Boolean

    companion object {
        fun createInstance(homeRepository: HomeRepository): HomeUseCase = HomeUseCaseImpl(homeRepository)
    }
}