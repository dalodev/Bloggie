/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import es.littledavity.dynamicfeatures.newpost.databinding.FragmentNewPostBinding

object ChangePostNameDialog {

    /**
     * Create a dialog to change title of post
     */
    fun builder(context: Context?, layoutInflater: LayoutInflater, viewBinding: FragmentNewPostBinding, viewModel: NewPostViewModel) {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            val viewContent: View = layoutInflater.inflate(R.layout.title_edit_text_dialog, null)
            dialogBuilder.setView(viewContent)
            val editText: EditText = viewContent.findViewById(R.id.post_title_edittext)
            dialogBuilder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                viewBinding.collapsingToolBar.title = editText.text.toString()
                viewModel.data.value?.title = editText.text.toString()
                dialog.dismiss()
            }

            val dialog: AlertDialog = dialogBuilder.create()
            dialog.show()
            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewBinding.collapsingToolBar.title = editText.text.toString()
                    viewModel.data.value?.title = editText.text.toString()
                    dialog.dismiss()
                }
                false
            }
        }
    }
}
