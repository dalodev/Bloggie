package es.chewiegames.bloggie.viewmodel

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.*
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.PostContent
import es.chewiegames.domain.usecases.newpost.StoreNewPostUseCase
import kotlin.math.ceil
import kotlin.math.sqrt

class NewPostViewModel(private val context: Context, private val storeNewPostUseCase: StoreNewPostUseCase) : ViewModel() {

    val postContent: BaseSingleLiveEvent<ArrayList<PostContent>> by lazy { BaseSingleLiveEvent<ArrayList<PostContent>>() }
    val post: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val photoUriContent: BaseSingleLiveEvent<Uri> by lazy { BaseSingleLiveEvent<Uri>() }
    val photoSize: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val navigateToHome: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val showTitleDialog: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showInstructions: BaseSingleLiveEvent<Boolean> by lazy { BaseSingleLiveEvent<Boolean>() }
    val showUndoPostContent: BaseSingleLiveEvent<PostContent> by lazy { BaseSingleLiveEvent<PostContent>() }
    val postContentImageType: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val addAdapterItem: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val updateAdapterPosition: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val removeAdapterItem: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }

    private var tempContent: PostContent? = null
    var isTextContent: Boolean = true
    private var isTypeContent: Boolean = false
    private var typeContentToAdd: Int? = null

    init {
        typeContentToAdd = EDITTEXT_VIEW
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
                    //TODO: show error cannot found file or cannot read file
                    e.printStackTrace()
                }
            }
        }
    }

    private fun onAddImageContent(content: PostContent?, imageUri: Uri, bitmap: Bitmap) {
        if (content == null) {
            val tmpContent = PostContent()
            tmpContent.viewType = es.chewiegames.data.utils.IMAGE_VIEW
            tmpContent.position = postContent.value!!.size
            tmpContent.uriImage = imageUri.toString()
            tmpContent.bitmapImage = bitmap
            postContent.value?.add(tmpContent)
            onChangeViewType(IMAGE_VIEW, tmpContent.position)
        } else {
            content.uriImage = imageUri.toString()
            content.bitmapImage = bitmap
            onChangeImageContent(content.position)
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
            onChangeViewType(EDITTEXT_VIEW, content.position)
        }
    }

    fun publishPost(blogImageView: ImageView) {
        storeNewPostUseCase.blogImageView = blogImageView
        storeNewPostUseCase.postContent = postContent.value ?: arrayListOf()
//        storeNewPostUseCase.executeAsync(viewModelScope, post.value!!, onResult= {::onStoreNewPostSuccess}, onError = ::onTitleEmpty)
    }

    fun onAddContent() {
        if (isTextContent) {
            if (!isTypeContent) {
                isTextContent = true
                handleAddContent()
            }
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
        Utils.hideKeyBoard(context, view)
        if (textContent.trim().isNotEmpty()) {
            content.viewType = TEXT_VIEW
            content.content = textContent
            postContent.value!![postContent.value!!.indexOf(content)] = content
            onChangeViewType(TEXT_VIEW, content.position)
        } else {
            postContent.value?.remove(postContent.value!![postContent.value!!.indexOf(content)])
            removeAdapterItem.value = postContent.value!!.indexOf(content)
        }
    }

    fun editTextContent(content: PostContent) {
        content.viewType = EDITTEXT_VIEW
        onChangeViewType(EDITTEXT_VIEW, content.position)
    }

    fun itemSwiped(deletedItem: PostContent?, deletedItemIndex: Int) {
        if (deletedItem!!.viewType != EDITTEXT_VIEW) {
            showUndoPostContent.value = deletedItem
        } else if (deletedItem.viewType != IMAGE_VIEW) {
            isTypeContent = false
        }
        showInstructions.call()
    }

    private fun onChangeViewType(viewType: Int, position: Int) {
        typeContentToAdd = viewType
        if (viewType == IMAGE_VIEW) {
            //isTypeContent = false;
            addAdapterItem.call()
        } else {
            updateAdapterPosition.value = position
        }
        showInstructions.call()
    }

    private fun onChangeImageContent(position: Int) {
        updateAdapterPosition.value = position
    }

    /**
     * storeUseCase methods
     */
    private fun onStoreNewPostSuccess(any: LiveData<Unit>) = navigateToHome.call()

    private fun onTitleEmpty(t: Throwable) = showTitleDialog.call()

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
}