package es.chewiegames.bloggie.ui.detailPost

import es.chewiegames.bloggie.ui.ui.BaseView
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.model.PostContent

interface DetailPostView : BaseView {
    fun setAdapter(content: ArrayList<PostContent>)
    fun fillValues(post: Post)
    fun displayExpandedImage(content: String)
    fun goBack()
    fun closeExpandedImage()
}