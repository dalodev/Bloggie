package es.chewiegames.domain.usecases.feedpost

import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.usecases.UseCase

class FeedPostUseCase(private val repository: PostDataRepository) : UseCase<Unit, OnLoadFeedPostListener>() {

    override fun runInBackground(params: OnLoadFeedPostListener) {
        repository.loadFeedPost(object : OnLoadFeedPostCallback{
            override fun onItemAdded(post: PostData) {
                params.onItemAdded(post as Post)
            }

            override fun onItemRemoved(position: Int) {
                params.onItemRemoved(position)
            }

            override fun onItemChange(position: Int, post: PostData) {
                params.onItemChange(position, post as Post)
            }
        })
    }
}