package es.chewiegames.domain.usecases.newpost

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.data.repositoryImpl.NewPostDataRepository
import es.chewiegames.domain.model.*
import es.chewiegames.domain.usecases.UseCase

class StoreNewPostUseCase(private val repository: NewPostRepository)  {

    lateinit var blogImageView: ImageView
    var postContent = arrayListOf<PostContent>()


}