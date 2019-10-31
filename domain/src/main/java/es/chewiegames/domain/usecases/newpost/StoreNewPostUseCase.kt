package es.chewiegames.domain.usecases.newpost

import android.widget.ImageView
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.domain.usecases.UseCase

class StoreNewPostUseCase(private val repository : NewPostRepository) : UseCase<Unit, ImageView>() {

    override fun runInBackground(params: ImageView) {
        repository.storeNewPost(params)
    }

}