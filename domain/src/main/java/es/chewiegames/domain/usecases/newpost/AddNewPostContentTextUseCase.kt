package es.chewiegames.domain.usecases.newpost

import es.chewiegames.data.callbacks.OnPostContentCallback
import es.chewiegames.data.repositoryImpl.NewPostDataRepository
import es.chewiegames.domain.callbacks.OnPostContentListener
import es.chewiegames.domain.usecases.UseCase

class AddNewPostContentTextUseCase(private val repository: NewPostDataRepository) : UseCase<Unit, OnPostContentListener>(), OnPostContentCallback {

    lateinit var listener: OnPostContentListener
    override fun runInBackground(params: OnPostContentListener) {
        this.listener = params
        repository.onAddTextContent(this)
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