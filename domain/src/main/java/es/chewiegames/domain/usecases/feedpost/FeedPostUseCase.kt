package es.chewiegames.domain.usecases.feedpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import es.chewiegames.data.callbacks.OnLoadFeedPostCallback
import es.chewiegames.data.model.PostData
import es.chewiegames.data.repository.PostRepository
import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.domain.callbacks.OnLoadFeedPostListener
import es.chewiegames.domain.model.mapToPost
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow

class FeedPostUseCase(private val repository: PostRepository)   {


}