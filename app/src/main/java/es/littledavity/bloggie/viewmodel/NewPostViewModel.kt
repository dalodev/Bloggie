/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Callback
import es.littledavity.bloggie.livedata.BaseSingleLiveEvent
import es.littledavity.bloggie.util.BLOG_CONTENT_IMAGE
import es.littledavity.bloggie.util.BLOG_TITLE_IMAGE
import es.littledavity.bloggie.util.EDITTEXT_VIEW
import es.littledavity.bloggie.util.IMAGE_VIEW
import es.littledavity.bloggie.util.ImagePicker
import es.littledavity.bloggie.util.MAX_HEIGHT
import es.littledavity.bloggie.util.MAX_WIDTH
import es.littledavity.bloggie.util.TEXT_VIEW
import es.littledavity.bloggie.util.hideKeyBoard
import es.littledavity.data.exceptions.NewPostException
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.PostContent
import es.littledavity.domain.model.StoreNewPostParams
import es.littledavity.domain.usecases.newpost.StoreNewPostUseCase
import kotlin.math.ceil
import kotlin.math.sqrt

class NewPostViewModel(
    private val context: Context,
    private val storeNewPostUseCase: StoreNewPostUseCase
) : BaseViewModel() {

    val postContent: BaseSingleLiveEvent<ArrayList<PostContent>> by lazy { BaseSingleLiveEvent<ArrayList<PostContent>>() }
    val post: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val photoUriContent: BaseSingleLiveEvent<Uri> by lazy { BaseSingleLiveEvent<Uri>() }
    val photoSize: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val navigateToHome: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val showTitleDialog: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showInstructions: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showUndoContent: BaseSingleLiveEvent<PostContent> by lazy { BaseSingleLiveEvent<PostContent>() }
    val postContentImageType: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val addAdapterItem: BaseSingleLiveEvent<PostContent> by lazy { BaseSingleLiveEvent<PostContent>() }
    val updateAdapterPosition: BaseSingleLiveEvent<PostContent> by lazy { BaseSingleLiveEvent<PostContent>() }
    val removeAdapterItem: BaseSingleLiveEvent<PostContent> by lazy { BaseSingleLiveEvent<PostContent>() }
    val imageContentLoading: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val imageBackgroundVisibility: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }

    private var tempContent: PostContent? = null
    var isTextContent: Boolean = true
    private var isTypeContent: Boolean = false
    private var typeContentToAdd: Int = EDITTEXT_VIEW

    init {
        postContent.value = arrayListOf()
        post.value = Post()
        showInstructions()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            val photoUri = data.data
            if (photoUri != null) {
                try {
                    val bitmap = ImagePicker.getImageFromResult(context, data)
                    when (requestCode) {
                        BLOG_TITLE_IMAGE -> {
                            val size = ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
                            photoSize.value = size
                            photoUriContent.value = photoUri
                        }
                        BLOG_CONTENT_IMAGE -> onAddImageContent(tempContent, photoUri, bitmap!!)
                    }
                } catch (e: Exception) {
                    // TODO: show error cannot found file or cannot read file
                    e.printStackTrace()
                }
            }
        }
    }

    private fun onAddImageContent(content: PostContent?, imageUri: Uri, bitmap: Bitmap) {
        if (content == null) {
            val tmpContent = PostContent()
            tmpContent.viewType = IMAGE_VIEW
            tmpContent.position = postContent.value!!.size
            tmpContent.uriImage = imageUri.toString()
            tmpContent.bitmapImage = bitmap
            postContent.value?.add(tmpContent)
            onChangeViewType(IMAGE_VIEW, tmpContent.position, tmpContent)
        } else {
            content.uriImage = imageUri.toString()
            content.bitmapImage = bitmap
            onChangeImageContent(content)
        }
    }

    fun addTextContent() {
        isTextContent = true
        if (!isTypeContent) {
            isTypeContent = true
            val content = PostContent()
            content.viewType = EDITTEXT_VIEW
            content.position = postContent.value!!.size
            postContent.value?.add(content)
            onChangeViewType(EDITTEXT_VIEW, content.position, content)
        }
    }

    fun publishPost(blogImageView: ImageView) = storeNewPostUseCase.executeAsync(viewModelScope, StoreNewPostParams(post.value!!, postContent.value ?: arrayListOf(), blogImageView),
                    ::onStoreNewPostSuccess, ::onError, ::showProgressDialog, ::hideProgressDialog)

    fun onAddContent() {
        if (isTextContent && !isTypeContent) {
            isTextContent = true
            handleAddContent()
        } else {
            handleAddContent()
        }
    }

    private fun handleAddContent() {
        when (typeContentToAdd) {
            EDITTEXT_VIEW -> addTextContent()
            IMAGE_VIEW -> postContentImageType.value = BLOG_CONTENT_IMAGE
        }
    }

    fun doSetTextContent(view: View, content: PostContent, textContent: String) {
        isTypeContent = false
        typeContentToAdd = TEXT_VIEW
        hideKeyBoard(context, view)
        if (textContent.trim().isNotEmpty()) {
            content.viewType = TEXT_VIEW
            content.content = textContent
            postContent.value!![postContent.value!!.indexOf(content)] = content
            onChangeViewType(TEXT_VIEW, content.position, content)
        } else {
            postContent.value?.remove(postContent.value!![postContent.value!!.indexOf(content)])
            removeAdapterItem.value = content
        }
    }

    fun editTextContent(content: PostContent) {
        content.viewType = EDITTEXT_VIEW
        onChangeViewType(EDITTEXT_VIEW, content.position, content)
    }

    fun itemSwiped(deletedItem: PostContent?, deletedItemIndex: Int) {
        if (deletedItem!!.viewType != EDITTEXT_VIEW) {
            deletedItem.position = deletedItemIndex
            showUndoContent.value = deletedItem
        } else if (deletedItem.viewType != IMAGE_VIEW) {
            isTypeContent = false
        }
    }

    private fun onChangeViewType(viewType: Int, position: Int, content: PostContent) {
        typeContentToAdd = viewType
        if (viewType == IMAGE_VIEW) addAdapterItem.value = content
        else updateAdapterPosition.value = content
        showInstructions()
    }

    private fun onChangeImageContent(content: PostContent?) {
        updateAdapterPosition.value = content
        showInstructions()
    }

    private fun showInstructions() { showInstructions.value = postContent.value?.size == 0 }

    /**
     * storeUseCase methods
     * @param post post stored in database
     */
    private fun onStoreNewPostSuccess(post: Post) = navigateToHome.call()

    private fun onError(t: Throwable) {
        if (t is NewPostException) showTitleDialog.call()
        else error.value = t.message
    }

    /**
     * Trigger when click on add image to post
     */
    fun onPostContentImageClicked(content: PostContent?) {
        isTextContent = false
        tempContent = content
        postContentImageType.value = BLOG_CONTENT_IMAGE
    }

    fun onToolbarImageClicked() {
        isTypeContent = false
        tempContent = null
        postContentImageType.value = BLOG_TITLE_IMAGE
    }

    val postContentImageCallback = object : Callback {
        override fun onSuccess() {
            imageContentLoading.value = View.GONE
            imageBackgroundVisibility.value = View.VISIBLE
        }

        override fun onError() {
            imageContentLoading.value = View.GONE
            imageBackgroundVisibility.value = View.VISIBLE
        }
    }
}
