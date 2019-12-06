package es.chewiegames.data.repositoryImpl

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import es.chewiegames.data.exceptions.NewPostException
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.data.utils.IMAGE_VIEW
import java.io.ByteArrayOutputStream
import java.util.Calendar
import java.util.UUID
import kotlin.collections.ArrayList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "NewPostDataRepository"

@ExperimentalCoroutinesApi
class NewPostDataRepository(
    val mDatabasePosts: DatabaseReference,
    val mDatabasePostByUser: DatabaseReference,
    val mStorageReference: StorageReference,
    val mUserData: UserData
) : NewPostRepository {

    private lateinit var mPost: PostData
    private var postContent = arrayListOf<PostContentData>()

    override fun storeNewPost(post: PostData, postContentData: ArrayList<PostContentData>, blogImageView: ImageView): Flow<PostData> = callbackFlow {
        try {
            mPost = post
            postContent = postContentData
            if (mPost.title != null && mPost.title!!.isNotEmpty()) {
                val idP = mDatabasePosts.push().key
                mPost.id = idP!!
//                getDataFromTitleImage(blogImageView, idP)
                storeImage(getBitmapFromView(blogImageView), idP) // launch in coroutine???????
                mPost.createdDate = Calendar.getInstance().time
                mPost.userData = mUserData
                mPost.littlePoints = 0
                mPost.views = 0
                mPost.content = getContentData(idP)
                mDatabasePostByUser.child(idP).setValue(mPost) // Store post by user
                mPost.id = idP
                mDatabasePosts.child(idP).setValue(mPost) // store in general posts
                offer(mPost)
            } else {
                close(NewPostException("Title Empty"))
            }
        } catch (e: Exception) {
            Log.i(TAG, e.message!!)
            if (e is NewPostException) close(NewPostException("Title Empty"))
            else close(e)
        }
        awaitClose()
    }

    private fun getBitmapFromView(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable
        var imageInByte: ByteArray? = null
        if (drawable != null) {
            val bitmapDrawable = drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            if (bitmap != null) {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imageInByte = stream.toByteArray()
            }
        }
        return imageInByte
    }

    private fun storeImage(imageInByte: ByteArray?, idPost: String) {
        if (imageInByte != null) {
            val postImagesRefByUser = mStorageReference.child("images").child(idPost).child(UUID.randomUUID().toString())
            val uploadTaskByUser = postImagesRefByUser.putBytes(imageInByte)
            uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl
                        mDatabasePostByUser.child(idPost).setValue(mPost) // Store post by user
                    }
                }
            }
            val postImagesRefPost = mStorageReference.child("images/" + idPost + "/" + UUID.randomUUID() + ".jpg")
            val uploadTaskPost = postImagesRefPost.putBytes(imageInByte)
            uploadTaskPost.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl
                        mDatabasePosts.child(idPost).setValue(mPost) // store in general posts
                    }
                }
            }
        }
    }

    private fun getContentData(idPost: String): ArrayList<PostContentData> {
        for (i in 0 until postContent.size) {
            val content = postContent[i]
            if (content.viewType == IMAGE_VIEW) {
                uploadImage(content, idPost)
            }
        }
        return postContent
    }

    private fun uploadImage(content: PostContentData, idPost: String) {
        val baos = ByteArrayOutputStream()
        content.bitmapImage!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val postImagesRefByUser = mStorageReference.child("images").child(idPost).child(Uri.parse(content.uriImage).lastPathSegment!!)
        val uploadTaskByUser = postImagesRefByUser.putBytes(data)

        uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                val downloadUrl = it.toString()
                if (downloadUrl.isNotEmpty()) {
                    content.content = downloadUrl.toString()
                    mPost.content[content.position] = content
                    mDatabasePostByUser.child(idPost).setValue(mPost) // Store post by user
                }
            }
        }
        content.bitmapImage = null
    }
}
