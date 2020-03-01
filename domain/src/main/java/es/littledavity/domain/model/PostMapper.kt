/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import androidx.annotation.VisibleForTesting
import es.littledavity.core.api.responses.BaseResponse
import es.littledavity.core.api.responses.CommentResponse
import es.littledavity.core.api.responses.DataResponse
import es.littledavity.core.api.responses.PostContentResponse
import es.littledavity.core.api.responses.PostResponse
import es.littledavity.core.mapper.Mapper
import javax.inject.Inject

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class PostMapper @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val userMapper: UserMapper,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val postContentMapper: PostContentMapper,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val commentMapper: CommentMapper
) : Mapper<BaseResponse<PostResponse>, List<Post>> {

    /**
     * Transform firebase response to [Post].
     *
     * @param from data post response.
     * @return List of parsed post items.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: BaseResponse<PostResponse>) =
        from.data.results.map {
            Post(
                id = it.id,
                title = it.title,
                titleImage = it.titleImage,
                content = postContentMapper.listMap(it.content) as ArrayList<PostContent>,
                comments = commentMapper.listMap(it.comments) as ArrayList<Comment>,
                littlePoints = it.littlePoints,
                views = it.views,
                createdDate = it.createdDate,
                user = userMapper.map(it.user)
            )
        }

    /**
     * Transform firebase response to [Post].
     *
     * @param from data post response.
     * @return List of parsed post items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun reverseMap(from: List<Post>): BaseResponse<PostResponse> {
        return BaseResponse(
            data = DataResponse(
                offset = 0,
                limit = 20,
                total = from.size,
                count = 20,
                results = from.map {
                    PostResponse(
                        id = it.id,
                        title = it.title,
                        titleImage = it.titleImage,
                        content = postContentMapper.reverseListMap(it.content) as ArrayList<PostContentResponse>,
                        comments = commentMapper.reverseListMap(it.comments) as ArrayList<CommentResponse>,
                        littlePoints = it.littlePoints,
                        views = it.views,
                        createdDate = it.createdDate,
                        user = userMapper.reverseMap(it.user)
                    )
                }
            )
        )
    }
}
