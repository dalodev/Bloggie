package es.chewiegames.data.callbacks

import es.chewiegames.data.model.PostData

interface OnLoadFeedPostCallback {
    fun onItemAdded(post: PostData)

    fun onItemRemoved(idRemoved: String)

    fun onItemChange(post: PostData)
}
