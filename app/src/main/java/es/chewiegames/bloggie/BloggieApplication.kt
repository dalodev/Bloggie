package es.chewiegames.bloggie

import android.app.Application
import android.content.Context
import es.chewiegames.bloggie.koin.appModule
import es.chewiegames.data.koin.dataModule
import es.chewiegames.domain.koin.domainModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BloggieApplication : Application() {

    operator fun get(context: Context): BloggieApplication {
        return context.applicationContext as BloggieApplication
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        startKoin(this, listOf(appModule, domainModule, dataModule))
    }
}
