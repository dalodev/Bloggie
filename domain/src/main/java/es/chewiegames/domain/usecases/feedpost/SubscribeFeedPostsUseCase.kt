package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import es.chewiegames.data.repository.PostRepository
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.model.mapToPost
import es.chewiegames.domain.usecases.SubscribeUseCase

class SubscribeFeedPostsUseCase(private val repository: PostRepository) : SubscribeUseCase<Unit, OnLoadFeedPostListener>(), OnLoadFeedPostCallback {

    lateinit var mListener: OnLoadFeedPostListener

    override fun subscribe(callback: OnLoadFeedPostListener) {
        setListener(callback)
        repository.subscribeFeedPosts(this)
    }

    override fun onItemAdded(post: PostData) {
        mListener.onItemAdded(mapToPost(post))
    }

    override fun onItemRemoved(idRemoved: String) {
        mListener.onItemRemoved(idRemoved)
    }

    override fun onItemChange(post: PostData) {
        mListener.onItemChange(mapToPost(post))
    }

    override fun setListener(listener: OnLoadFeedPostListener) {
        this.mListener = listener
    }
}