package es.chewiegames.data.callbacks

import es.chewiegames.bloggie.model.Post
import java.util.ArrayList

interface OnLoadFinishedListener {
    fun onError(message: String)

    fun onSuccess(posts: ArrayList<Post>)

    fun onItemAdded()

    fun onItemRemoved(position: Int)

    fun onItemChange(position: Int)

    fun showProgressDialog()

    fun hideProgressDialog()
}