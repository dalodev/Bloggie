package es.chewiegames.domain.callbacks

import es.chewiegames.domain.model.Post
import java.util.ArrayList

interface OnLoadFeedPostListener {

    fun onLoadFeedPostSuccess(posts: ArrayList<Post>)

    fun onItemAdded(post: Post)

    fun onItemRemoved(position: Int)

    fun onItemChange(position: Int, post: Post)
}