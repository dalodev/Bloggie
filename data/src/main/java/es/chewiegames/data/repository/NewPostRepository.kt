package es.chewiegames.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import es.chewiegames.data.callbacks.OnPostContentCallback
import es.chewiegames.data.callbacks.OnStoreFinishedCallback
import es.chewiegames.data.model.PostContentData

interface NewPostRepository {
    fun onAddImageContent(content: PostContentData?, bitmap: Bitmap, uri: Uri, callback: OnPostContentCallback)
    fun onAddTextContent(callback: OnPostContentCallback)
    fun onEditTextContent(content: PostContentData, callback: OnPostContentCallback)
    fun setTextContent(content: PostContentData, textContent: String, callback: OnPostContentCallback)
    fun storePostTitle(title: String)
    fun storeNewPost(blogImageView: ImageView)
    fun handleAddContent(callback: OnPostContentCallback)
}