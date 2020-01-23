/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list.model

import es.littledavity.core.api.responses.BaseResponse
import es.littledavity.core.api.responses.PostResponse
import es.littledavity.core.mapper.Mapper

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class PostMapper : Mapper<BaseResponse<PostResponse>, List<Post>> {

    /**
     * Transform firebase response to [Post].
     *
     * @param from Network characters response.
     * @return List of parsed characters items.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: BaseResponse<PostResponse>) =
        from.data.results.map {
            Post(
                id = it.id,
                title = it.title,
                titleImage = it.titleImage,
                content = it.content,
                comments = it.comments,
                littlePoints = it.littlePoints,
                views = it.views,
                createdDate = it.createdDate,
                user = it.user
            )
        }
}
