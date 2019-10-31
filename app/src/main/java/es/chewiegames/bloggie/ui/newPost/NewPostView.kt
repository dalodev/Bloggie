package es.chewiegames.bloggie.ui.newPost

import es.chewiegames.bloggie.ui.ui.BaseView
import es.chewiegames.data.model.PostContentData
import android.content.Intent
import android.net.Uri

interface NewPostView : BaseView {
    fun setAdapter()
    fun onChangeTitle(title: String)
    fun onStartActivityForResult(intent: Intent, photoPicker: Int, request: Int)
    fun onTitleImageSelected(imageUri: Uri, size: Int)
    fun navigateToHomeActivity()
    fun showTitleNameDialog()
    fun updateAdapterView(position: Int)
    fun showInstructions()
    fun removeContent(position: Int)
    fun addItem()
    fun showUndoSnackbar(deletedItem: PostContentData, deletedIndex: Int)
}