package es.chewiegames.bloggie.presenter.newPost

import es.chewiegames.bloggie.model.PostContent
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
    fun onAddImageContent(content: PostContent?, imageUri: Uri, bitmap: Bitmap)
    fun onChoosePhotoPicker(imageViewRequest: Int, content: PostContent?)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent, activity: Activity)
    fun publishPost(blogImageView: ImageView)
    fun setTextContent(content: PostContent?, textContent: String)
    fun onAddContent()
    fun editTextContent(content: PostContent?)
    fun isTypyng(): Boolean
    fun setTyping(typing: Boolean)
    fun itemSwiped(deletedItem: PostContent?, deletedItemIndex: Int)
}