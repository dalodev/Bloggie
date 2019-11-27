package es.chewiegames.bloggie.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val error: BaseSingleLiveEvent<String> by lazy { BaseSingleLiveEvent<String>() }
    val message: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }
    val loading: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }

    protected fun showProgressDialog() {
        loading.value = View.VISIBLE
    }

    protected fun hideProgressDialog() {
        loading.value = View.GONE
    }
}
