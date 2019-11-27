package es.chewiegames.bloggie.ui.ui

import androidx.lifecycle.LifecycleOwner

interface BaseView : LifecycleOwner {
    fun showMessage(message: String)
    fun showLoading(show: Boolean)
}
