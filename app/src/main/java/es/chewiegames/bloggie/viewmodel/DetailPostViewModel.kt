package es.chewiegames.bloggie.viewmodel

import android.content.Context

class DetailPostViewModel(private val context: Context) : BaseViewModel() {

    fun setCurrentAnimatorDuration() {
        val mShortAnimationDuration = context.resources.getInteger(android.R.integer.config_shortAnimTime)
        mDetailPostInteractor.setCurrentAnimatorDuration(mShortAnimationDuration)
    }
}
