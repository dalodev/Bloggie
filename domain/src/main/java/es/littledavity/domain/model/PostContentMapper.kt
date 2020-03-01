/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import es.littledavity.core.api.responses.PostContentResponse
import es.littledavity.core.mapper.Mapper
import javax.inject.Inject

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class PostContentMapper @Inject constructor() : Mapper<PostContentResponse, PostContent> {

    /**
     * Transform firebase response to [PostContent].
     *
     * @param from Network PostContentResponse.
     * @return List of parsed PostContent items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun map(from: PostContentResponse): PostContent {
        return PostContent(
            position = from.position,
            content = from.content,
            viewType = from.viewType,
            uriImage = from.uriImage,
            bitmapImage = from.bitmapImage
        )
    }

    /**
     * Transform postContent to [PostContentResponse].
     *
     * @param from data postContent response.
     * @return parsed PostContentResponse items.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun reverseMap(from: PostContent): PostContentResponse {
        return PostContentResponse(
            position = from.position,
            content = from.content,
            viewType = from.viewType,
            uriImage = from.uriImage,
            bitmapImage = from.bitmapImage
        )
    }

    suspend fun listMap(from: List<PostContentResponse>): List<PostContent> {
        val list = arrayListOf<PostContent>()
        from.map { list.add(map(it)) }
        return list
    }

    suspend fun reverseListMap(from: List<PostContent>): List<PostContentResponse> {
        val list = arrayListOf<PostContentResponse>()
        from.map { list.add(reverseMap(it)) }
        return list
    }
}
