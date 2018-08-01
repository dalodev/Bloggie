package es.chewiegames.bloggie.di.component

import dagger.Component
import es.chewiegames.bloggie.di.module.*
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
    fun plus(newPostModule: NewPostModule) : NewPostSubComponent
    fun plus(detailPostModule: DetailPostModule) : DetailPostSubComponent
}