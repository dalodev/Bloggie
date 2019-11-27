package es.chewiegames.bloggie.di.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import es.chewiegames.bloggie.BloggieApplication
import javax.inject.Singleton

@Module
class AppContextModule {

    @Provides
    @Singleton
    fun provideContext(): Context = BloggieApplication.instance

    @Provides
    @Singleton
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(BloggieApplication.instance)
    }
}
