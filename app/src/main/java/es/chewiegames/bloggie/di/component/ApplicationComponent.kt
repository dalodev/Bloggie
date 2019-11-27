package es.chewiegames.bloggie.di.component

import dagger.Component
import es.chewiegames.bloggie.di.module.AppContextModule
import es.chewiegames.bloggie.di.module.ApplicationModule
import es.chewiegames.bloggie.di.module.FirebaseDatabaseModule
import es.chewiegames.bloggie.di.module.FirebaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = (arrayOf(
        ApplicationModule::class,
        AppContextModule::class,
        FirebaseModule::class,
        FirebaseDatabaseModule::class
        )))
interface ApplicationComponent
