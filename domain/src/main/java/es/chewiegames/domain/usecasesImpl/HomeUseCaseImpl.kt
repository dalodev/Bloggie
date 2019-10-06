package es.chewiegames.domain.usecasesImpl

import es.chewiegames.bloggie.model.Post
import es.chewiegames.data.callbacks.OnLoadFinishedListener
import es.chewiegames.data.repository.HomeRepository
import es.chewiegames.domain.usecases.HomeUseCase

class HomeUseCaseImpl(homeRepository: HomeRepository) : HomeUseCase{

    override fun loadFeedPostsFromDatabase(listener: OnLoadFinishedListener) {
    }

    override fun storePostInDatabase(post: Post, listener: OnLoadFinishedListener) {
    }

    override fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadFinishedListener) {
    }

    override fun getLikedPostByUser(id: String, listener: OnLoadFinishedListener): Boolean {
    }
}