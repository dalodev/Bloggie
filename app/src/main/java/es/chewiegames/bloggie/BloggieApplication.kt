package es.chewiegames.bloggie

import android.app.Application
import android.content.Context
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.component.DaggerApplicationComponent

open class BloggieApplication : Application() {

    companion object {
        var applicationComponent: ApplicationComponent? = null
        lateinit var instance: BloggieApplication

        fun getComponent(): ApplicationComponent? {
            return applicationComponent
        }
    }
    operator fun get(context: Context): BloggieApplication {
        return context.applicationContext as BloggieApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeDependecyInjector()
    }

    private fun initializeDependecyInjector() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                //.applicationModule(ApplicationModule(this))
                .build()
    }
}