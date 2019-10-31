package es.chewiegames.bloggie.ui.detailPost

import es.chewiegames.bloggie.ui.ui.BaseView
import es.chewiegames.domain.model.Post
import es.chewiegames.data.model.PostContentData

interface DetailPostView : BaseView {
    fun setAdapter(content: ArrayList<PostContentData>)
    fun fillValues(post: Post)
    fun displayExpandedImage(content: String)
    fun goBack()
    fun closeExpandedImage()
}