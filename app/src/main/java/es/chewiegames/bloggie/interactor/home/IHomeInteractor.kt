package es.chewiegames.bloggie.interactor.home

import es.chewiegames.bloggie.interactor.BaseInteractor
import es.chewiegames.domain.model.Post
import java.util.ArrayList

interface IHomeInteractor : BaseInteractor {

    interface OnLoadFinishedListener {
        fun onError(message: String)

        fun onSuccess(posts: ArrayList<Post>)

        fun onItemAdded()

        fun onItemRemoved(position: Int)

        fun onItemChange(position: Int)

        fun showProgressDialog()

        fun hideProgressDialog()
    }

    fun loadFeedPostsFromDatabase(listener: OnLoadFinishedListener)
    fun storePostInDatabase(post: Post, listener: OnLoadFinishedListener)
    fun handleFromLikedPostByUser(post: Post, checked: Boolean, listener: OnLoadFinishedListener)
    fun getLikedPostByUser(id: String, listener: OnLoadFinishedListener): Boolean
}