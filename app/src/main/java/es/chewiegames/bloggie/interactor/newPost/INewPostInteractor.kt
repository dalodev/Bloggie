package es.chewiegames.bloggie.interactor.newPost

import es.chewiegames.data.model.PostContentData
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView

interface INewPostInteractor {

    interface PostContentListener {
        fun onChangeViewType(viewType: Int, position: Int)
        fun removeContent(position: Int)
        fun onAddImageContent()
        fun onChangeImageContent(position: Int)
    }

    interface OnStoreFinished {
        fun onStoreSuccess()
        fun onTitleEmpty()
    }

    fun onAddTextContent(listener: PostContentListener)
    fun onEditTextContent(content: PostContentData, listener: PostContentListener)
    fun onAddImageContent(content: PostContentData?, bitmap: Bitmap, uri: Uri, listener: PostContentListener)
    fun setTextContent(content: PostContentData, textContent: String, listener: PostContentListener)
    fun storePostTitle(s: String)
    fun storePostInDatabase(blogImageView: ImageView, listener: OnStoreFinished)
    fun handleAddContent(listener: PostContentListener)
}