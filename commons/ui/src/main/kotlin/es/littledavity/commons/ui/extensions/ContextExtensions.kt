/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes

/**
 * Get resource string from optional id
 *
 * @param resId Resource string identifier.
 * @return The key value if exist, otherwise empty.
 */
fun Context.getString(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }

/**
 * Hide keyboard
 *
 * @param view The token of the window that is making the request
 */
fun Context.hideKeyboard(view: View) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(
        view.windowToken,
        InputMethodManager.RESULT_UNCHANGED_SHOWN
    )
}
