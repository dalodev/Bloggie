package es.chewiegames.bloggie.interactor.newPost

import es.chewiegames.bloggie.model.PostContent
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
    fun onEditTextContent(content: PostContent, listener: PostContentListener)
    fun onAddImageContent(content: PostContent?, bitmap: Bitmap, uri: Uri, listener: PostContentListener)
    fun setTextContent(content: PostContent, textContent: String, listener: PostContentListener)
    fun storePostTitle(s: String)
    fun storePostInDatabase(blogImageView: ImageView, listener: OnStoreFinished)
    fun handleAddContent(listener: PostContentListener)
}