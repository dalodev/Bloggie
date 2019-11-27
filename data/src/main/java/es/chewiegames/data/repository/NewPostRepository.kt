package es.chewiegames.data.repository

import android.widget.ImageView
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData
import kotlinx.coroutines.flow.Flow

interface NewPostRepository {
    fun storeNewPost(post: PostData, postContentData: ArrayList<PostContentData>, blogImageView: ImageView): Flow<PostData>
}
