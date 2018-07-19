package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.MainActivityModule
import es.chewiegames.bloggie.di.scope.MainScope
import es.chewiegames.bloggie.ui.main.MainActivity

@MainScope
@Subcomponent(modules = (arrayOf(MainActivityModule::class)))
interface MainSubComponent {
    fun inject(mainActivity: MainActivity)
}