package es.chewiegames.bloggie.ui.ui

import android.arch.lifecycle.LifecycleOwner

interface BaseView : LifecycleOwner {
    fun showMessage(message : String)
    fun showLoading(show: Boolean)
}