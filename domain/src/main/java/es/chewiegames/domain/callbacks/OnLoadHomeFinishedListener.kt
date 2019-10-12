package es.chewiegames.domain.callbacks

import es.chewiegames.domain.model.Post
import java.util.ArrayList

interface OnLoadHomeFinishedListener {
    fun onError(message: String)

    fun onSuccess(posts: ArrayList<Post>)

    fun onItemAdded(post: Post)

    fun onItemRemoved(position: Int)

    fun onItemChange(position: Int, post: Post)

    fun showProgressDialog()

    fun hideProgressDialog()
}