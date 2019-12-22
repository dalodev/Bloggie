/*
 * Copyright 2019 littledavity
 */
package es.littledavity.domain.usecases.newpost

import es.littledavity.data.repository.NewPostRepository
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.StoreNewPostParams
import es.littledavity.domain.model.mapPostContentDataList
import es.littledavity.domain.model.mapToPost
import es.littledavity.domain.model.mapToPostData
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreNewPostUseCase(private val repository: NewPostRepository) : UseCase<Post, StoreNewPostParams>() {

    override fun runInBackground(params: StoreNewPostParams): Flow<Post> = repository.storeNewPost(
            mapToPostData(params.post), mapPostContentDataList(params.postContent), params.blogImageView
    ).map { mapToPost(it) }
}
