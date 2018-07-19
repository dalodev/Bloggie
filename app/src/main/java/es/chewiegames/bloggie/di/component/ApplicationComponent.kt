package es.chewiegames.bloggie.di.component

import dagger.Component
import es.chewiegames.bloggie.di.module.*
import es.chewiegames.bloggie.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = (arrayOf(
        ApplicationModule::class,
        AppContextModule::class,
        FirebaseModule::class,
        FirebaseDatabaseModule::class
        )))
interface ApplicationComponent {
    fun plus(loginModule: LoginModule) : LoginSubComponent
    fun plus(mainActivityModule: MainActivityModule) : MainSubComponent
    fun plus(homeModule: HomeModule) : HomeSubComponent
}