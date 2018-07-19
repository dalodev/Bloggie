package es.chewiegames.bloggie.di.component

import dagger.Component
import es.chewiegames.bloggie.di.module.ApplicationModule
import es.chewiegames.bloggie.di.module.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = (arrayOf(
        ApplicationModule::class
        )))
interface ApplicationComponent {
    fun plus(mainActivityModule: MainActivityModule) : MainSubComponent
}