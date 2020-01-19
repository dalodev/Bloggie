/*
 * Copyright 2020 littledavity
 */
package es.littledavity.bloggie.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.littledavity.bloggie.BloggieApp

/**
 * Class that contributes to the object graph [AppComponent].
 *
 * @see Module
 */
@Module
class AppModule {

    /**
     * Create a provider method binding for [Context].
     *
     * @param application Sample Application.
     * @return Instance of context.
     * @see Provides
     */
    @Provides
    fun provideContext(application: BloggieApp): Context = application.applicationContext
}
