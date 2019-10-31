package es.chewiegames.bloggie.presenter.newPost

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.EditText
import android.widget.ImageView
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.interactor.newPost.INewPostInteractor
import es.chewiegames.bloggie.interactor.newPost.NewPostInteractor
import es.chewiegames.data.model.PostContentData
import es.chewiegames.bloggie.ui.newPost.NewPostView
import es.chewiegames.bloggie.util.*
import javax.inject.Inject

class NewPostPresenter @Inject constructor() : INewPostPresenter, INewPostInteractor.PostContentListener, INewPostInteractor.OnStoreFinished {

    private var view: NewPostView? = null

    var isTextContent: Boolean = true

    private var isTypeContent: Boolean = false

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var interactor: NewPostInteractor

    private var tempContent: PostContentData? = null

    override fun setView(view: NewPostView) {
        this.view = view
    }

    override fun onChangePostTitle(activity: Activity) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val viewContent: View = activity.layoutInflater.inflate(R.layout.title_edit_text_dialog, null)
        dialogBuilder.setView(viewContent)
        val editText: EditText = viewContent.findViewById(R.id.post_title_edittext)
        dialogBuilder.setPositiveButton(activity.getString(R.string.ok)) { dialog, _ ->
            interactor.storePostTitle(editText.text.toString())
            view!!.onChangeTitle(editText.text.toString())
            dialog.dismiss()
        }

        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_DONE) {
                interactor.storePostTitle(editText.text.toString())
                view!!.onChangeTitle(editText.text.toString())
                dialog.dismiss()
            }
            false
        }
    }

    override fun onAddContent() {
        if (isTextContent) {
            if (!isTypeContent) {
                isTextContent = true
                interactor.handleAddContent(this)
            }
        } else {
            interactor.handleAddContent(this)
        }
    }

    override fun onAddTextContent(activity: Activity) {
        isTextContent = true
        if (!isTypeContent) {
            isTypeContent = true
            interactor.onAddTextContent(this)
        }
    }

    override fun onChoosePhotoPicker(imageViewRequest: Int, content: PostContentData?) {
        isTextContent = false
        tempContent = content
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        view!!.onStartActivityForResult(photoPickerIntent, PHOTO_PICKER, imageViewRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent, activity: Activity) {
        //isTypeContent= false;
        if (resultCode == RESULT_OK) {
            val photoUri = data.data
            if (photoUri != null) {
                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                    val bitmap = ImagePicker.getImageFromResult(context, data)
                    when (requestCode) {
                        BLOG_TITLE_IMAGE -> {
                            val size = Math.ceil(Math.sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
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

    override fun onAddImageContent(content: PostContentData?, imageUri: Uri, bitmap: Bitmap) {
        interactor.onAddImageContent(content, bitmap, imageUri, this)
    }


    override fun publishPost(blogImageView: ImageView) {
        interactor.storePostInDatabase(blogImageView, this)
    }

    override fun setTextContent(content: PostContentData?, textContent: String) {
        isTypeContent = false
        interactor.setTextContent(content!!, textContent, this)
    }

    override fun editTextContent(content: PostContentData?) {
        interactor.onEditTextContent(content!!, this)
    }

    override fun isTypyng(): Boolean {
        return isTypeContent
    }

    override fun setTyping(typing: Boolean) {
        isTypeContent = typing
    }

    override fun itemSwiped(deletedItem: PostContentData?, deletedItemIndex: Int) {
        if(deletedItem!!.viewType != EDITTEXT_VIEW){
            view!!.showUndoSnackbar(deletedItem, deletedItemIndex)
        }else if(deletedItem.viewType != IMAGE_VIEW){
            setTyping(false)
        }
        view!!.showInstructions()
    }

    //interactor PostContentListener
    override fun onChangeViewType(viewType: Int, position: Int) {
        if (viewType == IMAGE_VIEW) {
            //isTypeContent = false;
            view!!.addItem()
        } else {
            view!!.updateAdapterView(position)
        }
        view!!.showInstructions()
    }

    override fun removeContent(position: Int) {
        view!!.removeContent(position)
        view!!.showInstructions()
    }

    override fun onAddImageContent() {
        onChoosePhotoPicker(BLOG_CONTENT_IMAGE, null)
    }

    override fun onChangeImageContent(position: Int) {
        view!!.updateAdapterView(position)
    }

    //interactor OnStoreFinished
    override fun onStoreSuccess() {
        view!!.navigateToHomeActivity()
    }

    override fun onTitleEmpty() {
        view!!.showTitleNameDialog()
    }
}