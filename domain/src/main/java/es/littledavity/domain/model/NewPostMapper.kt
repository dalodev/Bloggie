/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import androidx.annotation.VisibleForTesting
import es.littledavity.core.api.responses.CommentResponse
import es.littledavity.core.api.responses.PostContentResponse
import es.littledavity.core.api.responses.PostResponse
import es.littledavity.core.mapper.Mapper
import javax.inject.Inject

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class NewPostMapper @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val userMapper: UserMapper,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val postContentMapper: PostContentMapper,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val commentMapper: CommentMapper
) : Mapper<PostResponse, Post> {

    /**
     * Transform firebase response to [Post].
     *
     * @param from Network characters response.
     * @return List of parsed characters items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun map(from: PostResponse): Post {
        return Post(
            id = from.id,
            title = from.title,
            titleImage = from.titleImage,
            content = postContentMapper.listMap(from.content) as ArrayList<PostContent>,
            comments = commentMapper.listMap(from.comments) as ArrayList<Comment>,
            littlePoints = from.littlePoints,
            views = from.views,
            createdDate = from.createdDate,
            user = userMapper.map(from.user)
        )
    }

    /**
     * Transform post to [PostResponse].
     *
     * @param from data post response.
     * @return data post parsed characters items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun reverseMap(from: Post): PostResponse {
        return PostResponse(
            id = from.id,
            title = from.title,
            titleImage = from.titleImage,
            content = postContentMapper.reverseListMap(from.content) as ArrayList<PostContentResponse>,
            comments = commentMapper.reverseListMap(from.comments) as ArrayList<CommentResponse>,
            littlePoints = from.littlePoints,
            views = from.views,
            createdDate = from.createdDate,
            user = userMapper.reverseMap(from.user)
        )
    }
}
