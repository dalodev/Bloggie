package es.chewiegames.data.callbacks

import es.chewiegames.data.model.PostData
import java.util.ArrayList

interface OnLoadFeedPostCallback {
    fun onItemAdded(post: PostData)

    fun onItemRemoved(position: Int)

    fun onItemChange(position: Int, post: PostData)
}