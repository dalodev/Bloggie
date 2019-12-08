package es.littledavity.domain.usecases.feedpost

import es.littledavity.data.callbacks.OnLoadFeedPostCallback
import es.littledavity.data.model.PostData
import es.littledavity.data.repository.PostRepository
import es.littledavity.domain.callbacks.OnLoadFeedPostListener
import es.littledavity.domain.model.mapToPost
import es.littledavity.domain.usecases.SubscribeUseCase

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
