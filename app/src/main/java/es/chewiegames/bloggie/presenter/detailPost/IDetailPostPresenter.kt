package es.chewiegames.bloggie.presenter.detailPost

import es.chewiegames.bloggie.presenter.BasePresenter
import es.chewiegames.bloggie.ui.detailPost.DetailPostView
import es.chewiegames.domain.model.Post
import es.chewiegames.data.model.PostContentData
import android.os.Bundle
import android.view.View
import android.widget.ImageView

interface IDetailPostPresenter : BasePresenter<DetailPostView> {
    fun loadData(extras: Bundle)
    fun setCurrentAnimatorDuration()
    fun zoomDetailPostImage(thumbView: View, expandedImage: ImageView, post: Post)
    fun zoomDetailPostImage(thumbView: View, expandedImage: ImageView, post: PostContentData?)
    fun closeExpandedImage(expandedImage: ImageView)
    fun handleBack()
    fun getPost(): Post?
}