package es.chewiegames.bloggie.ui

import android.os.Bundle
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.MainActivityModule
import es.chewiegames.bloggie.presenter.main.IMainPresenter
import es.chewiegames.bloggie.ui.ui.main.MainView
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var mMainPresenter : IMainPresenter

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(MainActivityModule(this, this)).inject(this)
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}
