package es.littledavity.data.repository

import android.widget.ImageView
import es.littledavity.data.model.PostContentData
import es.littledavity.data.model.PostData
import kotlinx.coroutines.flow.Flow

interface NewPostRepository {
    fun storeNewPost(
        post: PostData,
        postContentData: ArrayList<PostContentData>,
        blogImageView: ImageView
    ): Flow<PostData>
}
