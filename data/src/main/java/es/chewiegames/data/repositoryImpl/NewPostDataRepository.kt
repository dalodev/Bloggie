package es.chewiegames.data.repositoryImpl

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import es.chewiegames.data.callbacks.OnPostContentCallback
import es.chewiegames.data.exceptions.NewPostException
import es.chewiegames.data.model.PostContentData
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.data.utils.EDITTEXT_VIEW
import es.chewiegames.data.utils.IMAGE_VIEW
import es.chewiegames.data.utils.TEXT_VIEW
import java.io.ByteArrayOutputStream
import java.util.*

class NewPostDataRepository(private val mPost: PostData,
                            private val mDatabasePosts: DatabaseReference,
                            private val mDatabasePostByUser: DatabaseReference,
                            private val mStorageReference: StorageReference,
                            private val mUserData: UserData,
                            private val postContent: ArrayList<PostContentData> = arrayListOf()) : NewPostRepository {

    var typeContentToAdd : Int? = null

    init {
        typeContentToAdd = EDITTEXT_VIEW
    }

    override fun onAddImageContent(content: PostContentData?, bitmap: Bitmap, uri: Uri, callback: OnPostContentCallback) {
        typeContentToAdd = IMAGE_VIEW
        if (content == null) {
            val content = PostContentData()
            content.viewType = IMAGE_VIEW
            content.position = postContent.size
            content.uriImage = uri.toString()
            content.bitmapImage = bitmap
            postContent.add(content)
            listener.onChangeViewType(IMAGE_VIEW, content.position)
        } else {
            content.uriImage = uri.toString()
            content.bitmapImage = bitmap
            listener.onChangeImageContent(content.position)
        }
    }

    override fun onAddTextContent(callback: OnPostContentCallback) {
        typeContentToAdd = EDITTEXT_VIEW
        val content = PostContentData()
        content.viewType = EDITTEXT_VIEW
        content.position = postContent.size
        postContent.add(content)
        listener.onChangeViewType(EDITTEXT_VIEW, content.position)
    }

    override fun onEditTextContent(content: PostContentData, callback: OnPostContentCallback) {
        content.viewType = EDITTEXT_VIEW
        listener.onChangeViewType(EDITTEXT_VIEW, content.position)
    }

    override fun setTextContent(content: PostContentData, textContent: String, callback: OnPostContentCallback) {
        if(!textContent.trim().isEmpty()){
            content.viewType = TEXT_VIEW
            content.content = textContent
//            postContent.set(content.getPosition(), content)
            postContent[postContent.indexOf(content)] = content
            listener.onChangeViewType(TEXT_VIEW, content.position)
        }else{
            listener.removeContent(postContent.indexOf(content))
            postContent.remove(postContent[postContent.indexOf(content)])
        }
    }

    override fun storePostTitle(title: String) {
        mPost.title = title
    }

    override fun storeNewPost(blogImageView: ImageView) {
        try {
            if (mPost.title != null && mPost.title!!.isNotEmpty()) {
                val idP = mDatabasePosts.push().key
                mPost.id = idP!!
                getDataFromTitleImage(blogImageView, idP)
                mPost.createdDate = Calendar.getInstance().time
                mPost.userData = mUserData
                mPost.littlePoints = 0
                mPost.views = 0
                mPost.content = getContentData(idP)
                mDatabasePostByUser.child(idP).setValue(mPost) //Store post by user
                mPost.id = idP
                mDatabasePosts.child(idP).setValue(mPost)//store in general posts
            } else {
                throw NewPostException("Tittle Empty")
            }
        } catch (e: Exception) {
            throw Exception()
        }
    }

    override fun handleAddContent(callback: OnPostContentCallback) {
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
                    if (!downloadUrl.isEmpty()) {
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
                    if (!downloadUrl.isEmpty()) {
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
                if (!downloadUrl.toString().isEmpty()) {
                    content.content = downloadUrl
                    mPost.content[content.position] = content
                    mDatabasePostByUser.child(idPost).setValue(mPost) //Store post by user
                }
            }
        }
    }
}