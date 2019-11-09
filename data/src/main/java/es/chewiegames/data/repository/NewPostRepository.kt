package es.chewiegames.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import es.chewiegames.data.callbacks.OnPostContentCallback
import es.chewiegames.data.callbacks.OnStoreFinishedCallback
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData

interface NewPostRepository {
    fun storeNewPost(mPost: PostData, postContent : ArrayList<PostContentData>, blogImageView: ImageView)
}