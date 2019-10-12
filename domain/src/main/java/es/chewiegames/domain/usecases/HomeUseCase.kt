package es.chewiegames.domain.usecases

import es.chewiegames.domain.model.Post
import es.chewiegames.data.repository.HomeRepository
import es.chewiegames.domain.callbacks.OnLoadHomeFinishedListener
import es.chewiegames.domain.usecasesImpl.HomeUseCaseImpl

interface HomeUseCase {

    fun loadFeedPostsFromDatabase(listener: OnLoadHomeFinishedListener)
    fun storePostInDatabase(post: Post, listener: OnLoadHomeFinishedListener)
    fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadHomeFinishedListener)
    fun getLikedPostByUser(id: String, listener: OnLoadHomeFinishedListener): Boolean

    companion object {
        fun createInstance(homeRepository: HomeRepository): HomeUseCase = HomeUseCaseImpl(homeRepository)
    }
}