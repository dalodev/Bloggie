package es.chewiegames.bloggie.ui.mainactivity2

import android.content.Context
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.BaseFragment

class HomeFragment :BaseFragment() {

    override fun injectDependencies(component: ApplicationComponent, context: Context) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
}