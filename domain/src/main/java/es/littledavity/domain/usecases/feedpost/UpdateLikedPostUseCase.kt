/*
 * Copyright 2019 littledavity
 */
package es.littledavity.domain.usecases.feedpost

import es.littledavity.data.repository.PostRepository
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.PostParams
import es.littledavity.domain.model.mapToPost
import es.littledavity.domain.model.mapToPostData
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateLikedPostUseCase(private val repository: PostRepository) : UseCase<Post, PostParams>() {

    override fun runInBackground(params: PostParams): Flow<Post> =
            repository.updateLikedPosts(
                    mapToPostData(params.post),
                    params.checked).map { mapToPost(it) }
}
