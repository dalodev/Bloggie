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
import androidx.lifecycle.ViewModel
import com.david.pokeapp.livedata.BaseSingleLiveEvent
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.*
import es.chewiegames.data.model.PostContentData
import es.chewiegames.domain.callbacks.OnPostContentListener
import es.chewiegames.domain.model.PostContent
import es.chewiegames.domain.usecases.newpost.AddNewPostContentImageUseCase
import es.chewiegames.domain.usecases.newpost.AddNewPostContentTextUseCase
import es.chewiegames.domain.usecases.newpost.ChangeNewPostTitleUseCase
import es.chewiegames.domain.usecases.newpost.StoreNewPostUseCase
import kotlin.math.ceil
import kotlin.math.sqrt

class NewPostViewModel(private val context: Context,
                       private val storeNewPostUseCase: StoreNewPostUseCase,
                       private val changePostTitleUseCase: ChangeNewPostTitleUseCase,
                       private val addNewPostContentImageUseCase: AddNewPostContentImageUseCase,
                       private val addNewPostContentTextUseCase : AddNewPostContentTextUseCase) : ViewModel(), OnPostContentListener {

    val postContent: BaseSingleLiveEvent<ArrayList<PostContentData>> by lazy { BaseSingleLiveEvent<ArrayList<PostContentData>>() }

    lateinit var tempContent: PostContent
    var isTextContent: Boolean = true
    private var isTypeContent: Boolean = false

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent, activity: Activity) {
        //isTypeContent= false;
        if (resultCode == Activity.RESULT_OK) {
            val photoUri = data.data
            if (photoUri != null) {
                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                    val bitmap = ImagePicker.getImageFromResult(context, data)
                    when (requestCode) {
                        BLOG_TITLE_IMAGE -> {
                            val size = ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
                            view!!.onTitleImageSelected(photoUri, size)
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

    fun onChangePostTitle(activity: Activity) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val viewContent: View = activity.layoutInflater.inflate(R.layout.title_edit_text_dialog, null)
        dialogBuilder.setView(viewContent)
        val editText: EditText = viewContent.findViewById(R.id.post_title_edittext)
        dialogBuilder.setPositiveButton(activity.getString(R.string.ok)) { dialog, _ ->
            changePostTitleUseCase.executeAsync(editText.text.toString(), onSuccess = {}, onError = {})
            view!!.onChangeTitle(editText.text.toString())
            dialog.dismiss()
        }

        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                changePostTitleUseCase.executeAsync(editText.text.toString(), onSuccess = {}, onError = {})
                view!!.onChangeTitle(editText.text.toString())
                dialog.dismiss()
            }
            false
        }
    }

    private fun onAddImageContent(content: PostContent, imageUri: Uri, bitmap: Bitmap) {
        addNewPostContentImageUseCase.bitmap = bitmap
        addNewPostContentImageUseCase.imageUri = imageUri
        addNewPostContentImageUseCase.listener = this
        addNewPostContentImageUseCase.executeAsync(content, onSuccess = {}, onError = {})
    }

    fun onAddTextContent(activity: Activity) {
        isTextContent = true
        if (!isTypeContent) {
            isTypeContent = true,
            addNewPostContentTextUseCase.executeAsync(this, onSuccess = {}, onError = {})
            interactor.onAddTextContent(this)
        }
    }

    fun onChoosePhotoPicker(imageViewRequest: Int, content: PostContent) {
        isTextContent = false
        tempContent = content
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        view!!.onStartActivityForResult(photoPickerIntent, PHOTO_PICKER, imageViewRequest)
    }

    fun publishPost(blogImageView: ImageView) {
        storeNewPostUseCase.executeAsync(blogImageView, onSuccess =  {onStoreNewPostSuccess()}, onError = {onTitleEmpty()})
    }

    fun onAddContent() {
        if (isTextContent) {
            if (!isTypeContent) {
                isTextContent = true
                interactor.handleAddContent(this)
            }
        } else {
            interactor.handleAddContent(this)
        }
    }

    fun setTextContent(content: PostContentData?, textContent: String) {
        isTypeContent = false
        interactor.setTextContent(content!!, textContent, this)
    }

    fun editTextContent(content: PostContentData?) {
        interactor.onEditTextContent(content!!, this)
    }

    fun itemSwiped(deletedItem: PostContentData?, deletedItemIndex: Int) {
        if(deletedItem!!.viewType != EDITTEXT_VIEW){
            view!!.showUndoSnackbar(deletedItem, deletedItemIndex)
        }else if(deletedItem.viewType != IMAGE_VIEW){
            setTyping(false)
        }
        view!!.showInstructions()
    }

    fun setTyping(typing: Boolean) {
        isTypeContent = typing
    }

    private fun onStoreNewPostSuccess() {
        view!!.navigateToHomeActivity()
    }

    fun onTitleEmpty() {
        view!!.showTitleNameDialog()
    }

    /**
     * OnPostContent methods implementation
     */
    override fun onChangeViewType(viewType: Int, position: Int) {
    }

    override fun removeContent(position: Int) {
    }

    override fun onAddImageContent() {
    }

    override fun onChangeImageContent(position: Int) {
    }
}