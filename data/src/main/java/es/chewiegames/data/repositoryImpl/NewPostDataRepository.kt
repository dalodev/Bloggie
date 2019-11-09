package es.chewiegames.data.repositoryImpl

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import es.chewiegames.data.exceptions.NewPostException
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.data.utils.IMAGE_VIEW
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


class NewPostDataRepository(val mDatabasePosts: DatabaseReference,
                            val mDatabasePostByUser: DatabaseReference,
                            val mStorageReference: StorageReference,
                            val mUserData: UserData) : NewPostRepository {

    private lateinit var mPost: PostData
    private var postContent = arrayListOf<PostContentData>()

    override fun storeNewPost(mPost: PostData, postContent: ArrayList<PostContentData>, blogImageView: ImageView) {
        try {
            this.mPost = mPost
            this.postContent = postContent
            if (mPost.title != null && mPost.title!!.isNotEmpty()) {
                val idP = mDatabasePosts.push().key
                mPost.id = idP!!
//                getDataFromTitleImage(blogImageView, idP)
                mPost.createdDate = Calendar.getInstance().time
                mPost.userData = mUserData
                mPost.littlePoints = 0
                mPost.views = 0
                mPost.content = getContentData(idP)
                mDatabasePostByUser.child(idP).setValue(mPost) //Store post by user
                mPost.id = idP
                mDatabasePosts.child(idP).setValue(mPost)//store in general posts
                storeImage(getBitmapFromView(blogImageView), idP)  //launch in coroutine???????
            } else {
                throw NewPostException("Tittle Empty")
            }
        } catch (e: Exception) {
            throw NewPostException(e.message ?: "Default Exception")
        }
    }

    private fun getBitmapFromView(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        var imageInByte : ByteArray? = null
        if (bitmap != null) {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            imageInByte = stream.toByteArray()
            val bis = ByteArrayInputStream(imageInByte)
        }
        return imageInByte
    }

    private fun storeImage(imageInByte: ByteArray?, idPost: String) {
        if(imageInByte!=null){
            val postImagesRefByUser = mStorageReference.child("images").child(idPost).child(UUID.randomUUID().toString())
            val uploadTaskByUser = postImagesRefByUser.putBytes(imageInByte)
            uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl
                        mDatabasePostByUser.child(idPost).setValue(mPost) //Store post by user
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
                        mDatabasePosts.child(idPost).setValue(mPost)//store in general posts
                    }
                }
            }
        }else{
            throw Exception()
        }
    }

    private fun getDataFromTitleImage(imageView: ImageView, idPost: String) {
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val baos = ByteArrayOutputStream()
        val bitmap = imageView.drawingCache
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            imageView.destroyDrawingCache()
            imageView.isDrawingCacheEnabled = false // clear drawing cache
            val data = baos.toByteArray()

            val postImagesRefByUser = mStorageReference.child("images").child(idPost).child(UUID.randomUUID().toString())
            val uploadTaskByUser = postImagesRefByUser.putBytes(data)
            uploadTaskByUser.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl.toString()
                        mDatabasePostByUser.child(idPost).setValue(mPost) //Store post by user
                    }
                }
            }
            val postImagesRefPost = mStorageReference.child("images/" + idPost + "/" + UUID.randomUUID() + ".jpg")
            val uploadTaskPost = postImagesRefPost.putBytes(data)
            uploadTaskPost.addOnFailureListener { }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    if (downloadUrl.isNotEmpty()) {
                        mPost.titleImage = downloadUrl.toString()
                        mDatabasePosts.child(idPost).setValue(mPost)//store in general posts
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

        uploadTaskByUser.addOnFailureListener {
            val errorCode = (it as StorageException).errorCode
            val errorMessage = it.message
        }.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                val downloadUrl = it.toString()
                if (downloadUrl.isNotEmpty()) {
                    content.content = downloadUrl
                    mPost.content[content.position] = content
                    mDatabasePostByUser.child(idPost).setValue(mPost) //Store post by user
                }
            }
        }
    }
}