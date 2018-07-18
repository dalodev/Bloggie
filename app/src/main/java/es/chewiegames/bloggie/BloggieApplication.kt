package es.chewiegames.bloggie

import android.app.Application
import android.content.Context
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.ApplicationModule

class BloggieApplication : Application() {

    protected lateinit var applicationComponent: ApplicationComponent

    operator fun get(context: Context): BloggieApplication {
        return context.applicationContext as BloggieApplication
    }

    override fun onCreate() {
        super.onCreate()
        initializeDependecyInjector()
    }

    private fun initializeDependecyInjector() {
       /* applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()*/
    }

    open fun getComponent(): ApplicationComponent {
        return applicationComponent
    }

}