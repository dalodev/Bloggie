/*
 * Copyright 2019 littledavity
 */
package es.littledavity.domain.usecases.feedpost

import es.littledavity.data.repository.PostRepository
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.mapToPosts
import es.littledavity.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLikedPostsByUserUseCase(private val repository: PostRepository) : UseCase<ArrayList<Post>, UseCase.None>() {
    override fun runInBackground(params: None): Flow<ArrayList<Post>> = repository.getLikedPostsByUser().map { mapToPosts(it) }
}
