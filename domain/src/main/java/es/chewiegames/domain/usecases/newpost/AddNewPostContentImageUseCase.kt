package es.chewiegames.domain.usecases.newpost

import android.graphics.Bitmap
import android.net.Uri
import es.chewiegames.data.callbacks.OnPostContentCallback
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.repositoryImpl.NewPostDataRepository
import es.chewiegames.domain.callbacks.OnPostContentListener
import es.chewiegames.domain.model.PostContent
import es.chewiegames.domain.usecases.UseCase

class AddNewPostContentImageUseCase(private val repository: NewPostDataRepository) : UseCase<Unit, PostContent>(), OnPostContentCallback {

    lateinit var bitmap : Bitmap
    lateinit var imageUri : Uri
    lateinit var listener : OnPostContentListener

    override fun runInBackground(params: PostContent) {
        repository.onAddImageContent(params as PostContentData, bitmap, imageUri, this)
    }

    override fun onChangeViewType(viewType: Int, position: Int) {
        listener.onChangeViewType(viewType, position)
    }

    override fun removeContent(position: Int) {
        listener.removeContent(position)
    }

    override fun onAddImageContent() {
        listener.onAddImageContent()
    }

    override fun onChangeImageContent(position: Int) {
        listener.onChangeImageContent(position)
    }
}