package com.david.pokeapp.livedata

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.call() {
    value = null
}
