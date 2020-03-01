/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import androidx.annotation.VisibleForTesting
import es.littledavity.core.api.responses.CommentResponse
import es.littledavity.core.mapper.Mapper
import javax.inject.Inject

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class CommentMapper @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val userMapper: UserMapper
) : Mapper<CommentResponse, Comment> {

    /**
     * Transform firebase response to [Comment].
     *
     * @param from Network CommentResponse.
     * @return List of parsed Comment items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun map(from: CommentResponse): Comment {
        return Comment(
            comment = from.comment,
            replies = listMap(from.replies) as ArrayList<Comment>,
            likes = from.likes,
            user = userMapper.map(from.user),
            viewType = from.viewType
        )
    }

    /**
     * Transform postContent to [CommentResponse].
     *
     * @param from data Comment response.
     * @return parsed CommentResponse items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun reverseMap(from: Comment): CommentResponse {
        return CommentResponse(
            comment = from.comment,
            replies = reverseListMap(from.replies) as ArrayList<CommentResponse>,
            likes = from.likes,
            user = userMapper.reverseMap(from.user),
            viewType = from.viewType
        )
    }

    suspend fun listMap(from: List<CommentResponse>): List<Comment> {
        val list = arrayListOf<Comment>()
        from.map { list.add(map(it)) }
        return list
    }

    suspend fun reverseListMap(from: List<Comment>): List<CommentResponse> {
        val list = arrayListOf<CommentResponse>()
        from.map { list.add(reverseMap(it)) }
        return list
    }
}
