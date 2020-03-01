/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.littledavity.commons.ui.bindings.contentImage
import es.littledavity.commons.ui.extensions.hideKeyboard
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.api.repositories.NewPostRepository
import es.littledavity.core.exceptions.NewPostException
import es.littledavity.core.utils.BLOG_CONTENT_IMAGE
import es.littledavity.core.utils.BLOG_TITLE_IMAGE
import es.littledavity.core.utils.EDITTEXT_VIEW
import es.littledavity.core.utils.IMAGE_VIEW
import es.littledavity.core.utils.PERMISSION_CODE
import es.littledavity.core.utils.TEXT_VIEW
import es.littledavity.domain.model.NewPostMapper
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.PostContent
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewPostViewModel @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val repository: NewPostRepository,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val newPostMapper: NewPostMapper
) : ViewModel() {

    val event = SingleLiveData<NewPostViewEvent>()
    private val _data = MutableLiveData<Post>()
    val data: LiveData<Post>
        get() = _data
    private val _state = MutableLiveData<NewPostViewState>()
    val state: LiveData<NewPostViewState>
        get() = _state

    init {
        _state.value = NewPostViewState.Started
        _data.value = Post()
    }

    /**
     * Add image content item to data
     */
    fun addImageContentItem(imageUri: Uri, bitmap: Bitmap?, postContent: PostContent?) {
        _state.postValue(NewPostViewState.ImageViewType)
        getPost()?.apply {
            postContent?.let { c ->
                content.let {
                    it[it.indexOf(c)] = c
                }
            } ?: content.add(
                PostContent(
                    viewType = IMAGE_VIEW,
                    position = data.value!!.content.size,
                    uriImage = imageUri.toString(),
                    bitmapImage = bitmap
                )
            )
        }
    }

    /**
     * Add EditText content item to data
     */
    fun addEditTextContentItem(): Boolean {
        _state.postValue(NewPostViewState.EditTextViewType)
        getPost()?.apply {
            content.add(
                PostContent(
                    viewType = EDITTEXT_VIEW,
                    position = data.value!!.content.size
                )
            )
        }
        return true
    }

    /**
     * Add Text content item to data, if empty remove from data
     * Trigger on edit text item fab
     *
     * @param view The view who contain text data
     * @param content The object of adapter
     * @param text Te content text to add
     */
    fun addTextContentItem(view: View, content: PostContent, text: String) {
        view.context.hideKeyboard(view)
        if (text.trim().isNotEmpty()) {
            content.viewType = TEXT_VIEW
            content.content = text
            getContent()?.let {
                it[it.indexOf(content)] = content
            }
        } else {
            getContent()?.let {
                it.remove(it[it.indexOf(content)])
            }
        }
    }

    /**
     * Call repository to try to save in server
     *
     * @param blogImageView Image view to retrieve post title image
     */
    @ExperimentalCoroutinesApi
    fun publish(blogImageView: ImageView): Boolean {
        getPost()?.let {
            viewModelScope.launch {
                repository.storeNewPost(newPostMapper.reverseMap(it), getBitmapFromView(blogImageView))
                    .onEach { onStoreNewPostSuccess(newPostMapper.map(it)) }
                    .catch { onError(it) }
                    .launchIn(this)
            }
        }
        return true
    }

    /**
     * Request permissions if don't have permissions
     *
     * @param activity activity context
     * @param imageViewRequest Request id
     * @return Always return true
     */
    fun onAddImageClicked(activity: Activity, imageViewRequest: Int): Boolean {
        // check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                // permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                // show popup to request runtime permission
                requestPermissions(activity, permissions, PERMISSION_CODE)
            } else {
                // permission already granted
                pickImageFromGallery(imageViewRequest)
            }
        } else {
            // system OS is < Marshmallow
            pickImageFromGallery(imageViewRequest)
        }
        return true
    }

    /**
     * Triggered when user click on blank space in view
     */
    fun onAddContent() {
        when (state.value) {
            NewPostViewState.EditTextViewType -> addEditTextContentItem()
            NewPostViewState.ImageViewType -> _state.postValue(NewPostViewState.PickImageFromGalleryContent())
        }
    }

    /**
     * Triggered whe user click on title image
     */
    fun onToolbarImageClicked() {
        _state.postValue(NewPostViewState.PickImageFromGalleryTitle)
    }

    /**
     * Triggered whe user swipe post content item
     */
    fun itemSwiped(deletedItem: PostContent?, deletedItemIndex: Int) {
        if (deletedItem!!.viewType != EDITTEXT_VIEW) {
            deletedItem.position = deletedItemIndex
            _state.postValue(NewPostViewState.ItemDeleted(deletedItem))
        }
    }

    /**
     * Display content image
     */
    @BindingAdapter("displayImageContent")
    fun displayImageContent(view: ImageView, content: PostContent) {
        val imgUri: Uri? = Uri.parse(content.uriImage)
        val imgUrl: String? = content.content
        view.contentImage(imgUri, imgUrl)
    }

    /**
     * Trigger when click on add image to post
     */
    fun onPostContentImageClicked(content: PostContent?) {
        _state.postValue(NewPostViewState.PickImageFromGalleryContent(content))
    }

    // ============================================================================================
    //  Private setups methods
    // ============================================================================================

    /**
     * Get the array bytes from view
     *
     * @param imageView view with the bitmap
     * @return byte array of bitmap to store
     */
    private fun getBitmapFromView(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable
        var imageInByteArray: ByteArray? = null
        drawable?.let {
            val bitmapDrawable = it as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            bitmap?.let { bitmapImage ->
                val stream = ByteArrayOutputStream()
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imageInByteArray = stream.toByteArray()
            }
        }
        return imageInByteArray
    }

    /**
     * Request state to pick image from gallery
     */
    private fun pickImageFromGallery(imageViewRequest: Int) {
        when (imageViewRequest) {
            BLOG_TITLE_IMAGE -> _state.postValue(NewPostViewState.PickImageFromGalleryTitle)
            BLOG_CONTENT_IMAGE -> _state.postValue(NewPostViewState.PickImageFromGalleryContent())
        }
    }

    /**
     * storeUseCase methods
     * @param post post stored in database
     */
    private fun onStoreNewPostSuccess(post: Post) = event.postValue(NewPostViewEvent.OpenFeed)

    /**
     * Catch error on publish post
     */
    private fun onError(t: Throwable) {
        if (t is NewPostException) _state.postValue(NewPostViewState.Incomplete)
        else _state.postValue(NewPostViewState.Error)
    }

    private fun getPost() = _data.value

    private fun getContent() = getPost()?.content
}
