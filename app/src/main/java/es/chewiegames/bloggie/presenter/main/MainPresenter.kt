package es.chewiegames.bloggie.presenter.main

import es.chewiegames.bloggie.ui.ui.main.MainView
import javax.inject.Inject

class MainPresenter @Inject constructor() : IMainPresenter{

    private var view: MainView? = null

    override fun setView(view: MainView) {
        this.view = view
    }
}