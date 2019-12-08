package es.littledavity.domain.callbacks

import es.littledavity.domain.model.Post

interface OnLoadFeedPostListener {

    fun onItemAdded(post: Post)

    fun onItemRemoved(idRemoved: String)

    fun onItemChange(post: Post)
}
