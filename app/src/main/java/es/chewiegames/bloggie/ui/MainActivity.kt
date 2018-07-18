package es.chewiegames.bloggie.ui

import android.os.Bundle
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
    }

    override fun injectDependencies(component: ApplicationComponent) {
    }
}
