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
}