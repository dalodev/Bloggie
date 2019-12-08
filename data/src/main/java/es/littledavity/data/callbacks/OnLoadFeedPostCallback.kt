package es.littledavity.data.callbacks

import es.littledavity.data.model.PostData

interface OnLoadFeedPostCallback {
    fun onItemAdded(post: PostData)

    fun onItemRemoved(idRemoved: String)

    fun onItemChange(post: PostData)
}
