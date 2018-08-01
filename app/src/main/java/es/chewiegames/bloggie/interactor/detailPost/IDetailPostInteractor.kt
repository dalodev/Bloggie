package es.chewiegames.bloggie.interactor.detailPost

import android.view.View
import android.widget.ImageView
import es.chewiegames.bloggie.interactor.BaseInteractor
import es.chewiegames.bloggie.model.Post

interface IDetailPostInteractor : BaseInteractor{

    interface InteractorListener {
        fun displayExpandedImage(content: String)
    }

    fun setCurrentAnimatorDuration(mShortAnimationDuration: Int)
    fun zoomImageFromThumb(thumbView: View, expandedImage: ImageView, content: String, listener: InteractorListener)
    fun closeExpandedImage(expandedImage: ImageView)
    fun isExpandedImage(): Boolean
    fun addViewToPost(post: Post)
}