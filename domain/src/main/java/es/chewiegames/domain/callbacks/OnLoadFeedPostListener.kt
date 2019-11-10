package es.chewiegames.domain.callbacks

import es.chewiegames.domain.model.Post

interface OnLoadFeedPostListener {

    fun onItemAdded(post: Post)

    fun onItemRemoved(idRemoved: String)

    fun onItemChange(post: Post)
}