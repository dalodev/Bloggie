package es.chewiegames.bloggie.presenter

interface BasePresenter<T> {
    fun setView(view: T)
}
