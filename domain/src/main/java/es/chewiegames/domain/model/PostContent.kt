package es.chewiegames.domain.model

import android.graphics.Bitmap
import es.chewiegames.data.model.PostContentData
import java.io.Serializable

data class PostContent(
    var position: Int = -1,
    var content: String? = null,
    var viewType: Int = -1,
    var uriImage: String? = null,
    var bitmapImage: Bitmap? = null
) : Serializable

fun mapPostContentDataList(postContentList: List<PostContent>): ArrayList<PostContentData> {
    val postContentDataList = arrayListOf<PostContentData>()
    for (postContent in postContentList) {
        postContentDataList.add(mapToPostContentData(postContent))
    }
    return postContentDataList
}
fun mapPostContentList(postContentList: List<PostContentData>): ArrayList<PostContent> {
    val postContentDataList = arrayListOf<PostContent>()
    for (postContent in postContentList) {
        postContentDataList.add(mapToPostContent(postContent))
    }
    return postContentDataList
}
fun mapToPostContentData(postContent: PostContent) = PostContentData(postContent.position, postContent.content, postContent.viewType, postContent.uriImage, postContent.bitmapImage)
fun mapToPostContent(postContent: PostContentData) = PostContent(postContent.position, postContent.content, postContent.viewType, postContent.uriImage, postContent.bitmapImage)
