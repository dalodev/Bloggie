package es.chewiegames.bloggie.presenter.newPost

import es.chewiegames.data.model.PostContentData
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import es.chewiegames.bloggie.presenter.BasePresenter
import es.chewiegames.bloggie.ui.newPost.NewPostView

interface INewPostPresenter : BasePresenter<NewPostView> {
    fun onChangePostTitle(activity: Activity)
    fun onAddTextContent(activity: Activity)
    fun onAddImageContent(content: PostContentData?, imageUri: Uri, bitmap: Bitmap)
    fun onChoosePhotoPicker(imageViewRequest: Int, content: PostContentData?)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent, activity: Activity)
    fun publishPost(blogImageView: ImageView)
    fun setTextContent(content: PostContentData?, textContent: String)
    fun onAddContent()
    fun editTextContent(content: PostContentData?)
    fun isTypyng(): Boolean
    fun setTyping(typing: Boolean)
    fun itemSwiped(deletedItem: PostContentData?, deletedItemIndex: Int)
}