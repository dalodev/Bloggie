package es.littledavity.dynamicfeatures.feed.list.model

import es.littledavity.core.api.responses.PostResponse
import es.littledavity.core.mapper.Mapper

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class PostMapper : Mapper<PostResponse, Post> {

    /**
     * Transform firebase response to [Post].
     *
     * @param from Network characters response.
     * @return List of parsed characters items.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: PostResponse): Post {
        return Post(
            id = from.id,
            title = from.title,
            titleImage = from.titleImage,
            content = from.content,
            comments = from.comments,
            littlePoints = from.littlePoints,
            views = from.views,
            createdDate = from.createdDate,
            user = from.user
        )
    }
}
