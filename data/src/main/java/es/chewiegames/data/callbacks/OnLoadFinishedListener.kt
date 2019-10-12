package es.chewiegames.data.callbacks

import es.chewiegames.data.model.Post
import java.util.ArrayList

interface OnLoadFinishedListener {
    fun onError(message: String)

    fun onSuccess(posts: ArrayList<Post>)

    fun onItemAdded(post: Post)

    fun onItemRemoved(position: Int)

    fun onItemChange(position: Int, post: Post)

    fun showProgressDialog()

    fun hideProgressDialog()
}