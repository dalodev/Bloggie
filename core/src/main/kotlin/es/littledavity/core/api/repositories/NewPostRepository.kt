/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.repositories

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.VisibleForTesting
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import es.littledavity.core.api.responses.PostContentResponse
import es.littledavity.core.api.responses.PostResponse
import es.littledavity.core.exceptions.NewPostException
import es.littledavity.core.utils.IMAGE_VIEW
import java.io.ByteArrayOutputStream
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository module for handling new post data.
 */
class NewPostRepository @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Named("posts")
    internal val mDatabasePosts: DatabaseReference,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Named("postsByUser")
    internal val mDatabasePostByUser: DatabaseReference,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val mStorageReference: StorageReference
) {
    companion object {
        const val QUALITY = 100
    }

    fun storeNewPost(
        post: PostResponse,
        imageBytes: ByteArray?
    ): Flow<PostResponse> = flow {
        if (post.title.isNotEmpty() || imageBytes != null) {
            val idP = mDatabasePosts.push().key
            idP?.let {
                val mPost = PostResponse(
                    id = it,
                    createdDate = Calendar.getInstance().time,
                    user = post.user,
                    littlePoints = 0,
                    views = 0
                )
                mPost.content = getContentData(mPost)
                storeImage(imageBytes, mPost) // launch in coroutine???????
                mDatabasePostByUser.child(it).setValue(mPost) // Store post by user
                mDatabasePosts.child(it).setValue(mPost) // store in general posts
                emit(mPost)
            }
        } else {
            throw NewPostException()
        }
    }

    // ============================================================================================
    //  Private setups methods
    // ============================================================================================

    private fun storeImage(imageInByte: ByteArray?, mPost: PostResponse) {
        imageInByte?.let {
            val postImagesRefByUser = mStorageReference.child("images")
                .child(mPost.id)
                .child(UUID.randomUUID().toString())
            val uploadTaskByUser = postImagesRefByUser.putBytes(it)
            uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl
                        mDatabasePostByUser.child(mPost.id).setValue(mPost) // Store post by user
                    }
                }
            }
            val postImagesRefPost = mStorageReference.child("images/" + mPost.id + "/" + UUID.randomUUID() + ".jpg")
            val uploadTaskPost = postImagesRefPost.putBytes(it)
            uploadTaskPost.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl
                        mDatabasePosts.child(mPost.id).setValue(mPost) // store in general posts
                    }
                }
            }
        }
    }

    private fun getContentData(post: PostResponse): ArrayList<PostContentResponse> {
        post.content.forEach {
            if (it.viewType == IMAGE_VIEW) {
                uploadImage(it, post)
            }
        }
        return post.content
    }

    private fun uploadImage(content: PostContentResponse, post: PostResponse) {
        val baOs = ByteArrayOutputStream()
        content.bitmapImage?.compress(Bitmap.CompressFormat.JPEG, QUALITY, baOs)
        val data = baOs.toByteArray()
        val postImagesRefByUser = mStorageReference.child("images")
            .child(post.id)
            .child(Uri.parse(content.uriImage).lastPathSegment ?: return)
        val uploadTaskByUser = postImagesRefByUser.putBytes(data)

        uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                if (downloadUrl.isNotEmpty()) {
                    content.content = downloadUrl
                    post.content[content.position] = content
                    mDatabasePostByUser.child(post.id).setValue(post) // Store post by user
                }
            }
        }
        content.bitmapImage = null
    }
}
